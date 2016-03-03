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

package io.obrown.dockerweb.web;

import io.obrown.dockerweb.LocalDockerDaemon;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqHref;

/**
 * TkIndex page.
 * @author Armin Braun (me@obrown.io)
 * @version $Id$
 * @since 0.0.1
 */
public final class TkBuild implements Take {

    @Override
    public Response act(final Request request) throws IOException {
        final String res = new LocalDockerDaemon().run(
            "debian:jessie",
            new RqHref.Base(request).href().param("cmd").iterator().next()
        );
        final Map<String, String> params = new HashMap<>(1);
        params.put("result", res);
        return new HtmlResponse(
            "result.xsl",
            params
        ).render();
    }
}
