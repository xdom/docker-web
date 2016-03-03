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

import com.github.dockerjava.api.model.Info;
import io.obrown.dockerweb.LocalDockerDaemon;
import java.util.HashMap;
import java.util.Map;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;

/**
 * TkIndex page.
 * @author Armin Braun (me@obrown.io)
 * @version $Id$
 * @since 0.0.1
 */
public final class TkIndex implements Take {

    @Override
    public Response act(final Request request) {
        final Info info = new LocalDockerDaemon().info();
        final Map<String, String> params = new HashMap<>(2);
        params.put("status", info.getOperatingSystem());
        params.put("containers", Integer.toString(info.getContainers()));
        return new HtmlResponse(
            "home.xsl",
            params
        ).render();
    }
}
