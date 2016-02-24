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

import com.github.dockerjava.api.model.Info;
import java.io.IOException;

/**
 * Docker Daemon.
 * @author Armin Braun (me@obrown.io)
 * @version $Id$
 * @since 0.0.1
 */
public interface DockerDaemon {

    /**
     * Docker Info.
     * @return Info for the Docker daemon.
     */
    Info info();

    /**
     * Docker run command.
     * @param image String name of the image to be run.
     * @param cmd String of the command to be run.
     * @return String stdOut
     * @throws IOException for errors in Docker communication.
     */
    String run(String image, String cmd) throws IOException;
}
