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

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.google.common.base.Charsets;
import com.jcabi.manifests.Manifests;
import java.io.IOException;
import java.io.StringWriter;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.xembly.Xembler;

/**
 * Local Docker Daemon.
 * @author Armin Braun (me@obrown.io)
 * @version $Id$
 * @since 0.0.1
 */
public final class LocalDockerDaemon implements DockerDaemon {

    /**
     * DockerClientConfig.
     */
    private static final DockerClientConfig DOCKER_CONFIG =
        DockerClientConfig.createDefaultConfigBuilder()
            .withUri(Manifests.read("Docker-Host-Local"))
            .build();

    /**
     * Pattern for sanitizing invalid chars from Docker run output.
     */
    private static final Pattern SANITIZE_STDOUT =
        Pattern.compile("u0000|\\\\");

    /**
     * DockerClient instance.
     */
    private final DockerClient client;

    /**
     * Ctor.
     */
    public LocalDockerDaemon() {
        this.client = DockerClientBuilder.getInstance(
            LocalDockerDaemon.DOCKER_CONFIG
        ).build();
    }

    @Override
    public Info info() {
        return this.client.infoCmd().exec();
    }

    @Override
    public String run(final String image, final String cmd) throws IOException {
        final CreateContainerResponse container =
            this.client.createContainerCmd(image)
                .withTty(true)
                .withAttachStdin(true)
                .withCmd(
                    "/bin/bash",
                    "-c",
                    String.format("%s 1>>/tmp/out", cmd)
                )
                .exec();
        this.client.startContainerCmd(container.getId()).exec();
        this.client.waitContainerCmd(container.getId()).exec();
        final StringWriter writer = new StringWriter();
        IOUtils.copy(
            this.client.copyFileFromContainerCmd(
                container.getId(), "/tmp/out"
            ).exec(),
            writer,
            Charsets.UTF_8
        );
        return LocalDockerDaemon.SANITIZE_STDOUT
            .matcher(Xembler.escape(writer.toString())).replaceAll("");
    }
}
