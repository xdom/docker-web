/**
 * Docker Web
 * Copyright (C) 2016 Armin Braun
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.obrown.dockerweb;

import java.util.Map;
import org.takes.Response;
import org.takes.rs.RsWithType;
import org.takes.rs.RsXSLT;
import org.takes.rs.xe.RsXembly;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeStylesheet;
import org.xembly.Directive;
import org.xembly.Directives;

/**
 * HtmlResponse.
 * @author Armin Braun (me@obrown.io)
 * @version $Id$
 * @since 0.0.1
 */
public final class HtmlResponse {

    /**
     * Xsl file name.
     */
    private final String file;

    /**
     * Parameters for rendering output.
     */
    private final Map<String, String> parameters;

    /**
     * Ctor.
     * @param xsl String file name of the xsl.
     * @param params Map of the rendering parameters.
     */
    public HtmlResponse(final String xsl, final Map<String, String> params) {
        this.file = xsl;
        this.parameters = params;
    }

    /**
     * Renders output RsXSLT.
     * @return RsXSLT html response.
     */
    public Response render() {
        final Map<String, String> pars = this.parameters;
        return new RsXSLT(
            new RsWithType(
                new RsXembly(
                    new XeStylesheet(
                        String.format("src/main/resources/xsl/%s", this.file)
                    ),
                    new XeAppend(
                        "page",
                        new XeSource() {
                            @Override
                            public Iterable<Directive> toXembly() {
                                Directives directives = new Directives();
                                for (
                                    final Map.Entry<String, String> entry
                                        : pars.entrySet()
                                    ) {
                                    directives = directives.add(entry.getKey())
                                        .set(entry.getValue()).up();
                                }
                                return directives;
                            }
                        }
                    )
                ),
                "text/html"
            )
        );
    }
}
