/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kit.aifb.cumulus.cli;

import edu.kit.aifb.cumulus.cli.log.MessageCatalog;
import edu.kit.aifb.cumulus.store.Store;
import edu.kit.aifb.cumulus.store.sesame.CumulusRDFSail;

import edu.kit.aifb.geo.builder.BuildQuery;
import edu.kit.aifb.geo.builder.EjectCalculus;
import edu.kit.aifb.geo.builder.util.Components;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.openrdf.query.Binding;
import org.openrdf.query.BindingSet;
import org.openrdf.query.BooleanQuery;
import org.openrdf.query.GraphQuery;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.repository.sail.SailRepositoryConnection;

/**
 *
 * @author paul
 */
public class GQuery extends Command {

    @Override
    public void doExecute(CommandLine commandLine, Store store) {
        //final String query = commandLine.getOptionValue("q");
        final String query = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "PREFIX geoes: <http://geo.marmotta.es/ontology#>\n"
                + "PREFIX geo: <http://www.opengis.net/ont/geosparql#>\n"
                + "PREFIX geof: <http://www.opengis.net/def/function/geosparql/>\n"
                + "\n"
                + "SELECT DISTINCT ?labelMunicipios\n"
                + "WHERE {  \n"
                + "  ?subject a <http://geo.marmotta.es/ontology#municipio>.\n"
                + "  ?subject rdfs:label ?labelMunicipios.\n"
                + "  ?subject geoes:hasExactGeometry ?geo.\n"
                + "  ?geo geo:asWKT ?wkt.\n"
                + "  \n"
                + "  ?subject2 a <http://geo.marmotta.es/ontology#provincia>.\n"
                + "  ?subject2 rdfs:label \"Madrid\"@es.\n"
                + "  ?subject2 geoes:hasExactGeometry ?geo2.\n"
                + "  ?geo2 geo:asWKT ?wkt2.\n"
                + "\n"
                + "  FILTER (geof:sfWithin(?wkt, ?wkt2))  \n"
                + "}\n"
                + "ORDER BY ?labelMunicipios\n"
                + "LIMIT 10";

        SailRepositoryConnection con = null;
        SailRepository repo = null;
        BuildQuery convert;
        EjectCalculus op;
        try {
            final CumulusRDFSail sail = new CumulusRDFSail(store);
            sail.initialize();
            repo = new SailRepository(sail);
            con = repo.getConnection();

            convert = new BuildQuery();
            String gquery = convert.getQuery(query);
            EjectCalculus calcular = new EjectCalculus();
            long limit = convert.getLimit();
            long cont = 0;
            List<Components> condiciones = convert.getC();

            org.openrdf.query.Query parsed_query = con.prepareQuery(QueryLanguage.SPARQL, gquery);

            int i = 0;

            if (parsed_query instanceof BooleanQuery) {
                _log.info(MessageCatalog._00019_PARSED_ASK_QUERY, parsed_query);
                _log.info(MessageCatalog._00020_PARSED_ASK_ANSWER, ((BooleanQuery) parsed_query).evaluate());
            } else if (parsed_query instanceof TupleQuery) {
                _log.info(MessageCatalog._00021_PARSED_SELECT_ANSWER);
                for (final TupleQueryResult result = ((TupleQuery) parsed_query).evaluate(); result.hasNext(); i++) {

                    BindingSet bs = result.next();
                    List<String> est = new ArrayList<>();

                    for (Components c : condiciones) {
                        String operation = c.getOp();
                        String varx = bs.getBinding(c.getX()).getValue().stringValue();
                        String vary = bs.getBinding(c.getY()).getValue().stringValue();
                        if (calcular.topologicalRelations(operation, varx, vary)) {
                            est.add("s");
                        } else {
                            est.add("n");
                        }
                    }

                    if (!est.contains("n")) {
                        if (limit > 0 && cont < limit) {
                            _log.info(i + ": " + bs.getValue("labelMunicipios"));
                            cont++;
                        } else if (cont > limit) {
                            break;
                        }
                        if (limit < 0) {
                            _log.info(i + ": " + bs.getValue("labelMunicipios"));
                        }
                    }

                }
            } else if (parsed_query instanceof GraphQuery) {
                _log.info(MessageCatalog._00022_CONSTRUCT_ASK_QUERY, parsed_query);
                _log.info(MessageCatalog._00023_CONSTRUCT_ASK_ANSWER);
                for (final GraphQueryResult result = ((GraphQuery) parsed_query).evaluate(); result.hasNext(); i++) {
                    _log.info(i + ": " + result.next());
                }
            }

        } catch (final Exception exception) {
            _log.error(MessageCatalog._00026_NWS_SYSTEM_INTERNAL_FAILURE, exception);
            return;
        } finally {
            if (con != null) {
                // CHECKSTYLE:OFF
                try {
                    con.close();
                } catch (Exception ignore) {
                };
                // CHECKSTYLE: ON
            }

            if (repo != null) {
                // CHECKSTYLE:OFF
                try {
                    repo.shutDown();
                } catch (Exception ignore) {
                };
                // CHECKSTYLE: ON
            }
        }
    }

    @Override
    Options getOptions() {
        final Option inputO = new Option("q", "geosparql query string");
        inputO.setRequired(true);
        inputO.setArgs(1);

        final Option storageO = new Option("s", "storage layout to use (triple|quad)");
        storageO.setArgs(1);

        final Option helpO = new Option("h", "print help");

        final Options options = new Options();
        options.addOption(inputO);
        options.addOption(storageO);
        options.addOption(helpO);

        return options;
    }

}
