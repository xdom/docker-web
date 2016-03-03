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
package io.obrown.dockerweb.io.obrown.dockerweb.main;

import io.obrown.dockerweb.web.TkBuild;
import io.obrown.dockerweb.web.TkIndex;
import java.io.IOException;
import java.util.Arrays;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtCLI;

/**
 * Command line entry.
 * @author Armin Braun (me@obrown.io)
 * @version $Id$
 * @since 0.0.1
 */
public final class Entry {
    /**
     * Arguments.
     */
    private final transient Iterable<String> arguments;

    /**
     * Ctor.
     * @param args Command line args
     */
    public Entry(final String... args) {
        this.arguments = Arrays.asList(args);
    }

    /**
     * Main entry point.
     * @param args Arguments
     * @throws IOException If fails
     */
    public static void main(final String... args) throws IOException {
        new Entry(args).exec();
    }

    /**
     * Run it all.
     * @throws IOException If fails
     */
    public void exec() throws IOException {
        new FtCLI(
            new TkFork(
                new FkRegex("/build", new TkBuild()),
                new FkRegex("/", new TkIndex())
            ),
            this.arguments
        ).start(Exit.NEVER);
    }
}
