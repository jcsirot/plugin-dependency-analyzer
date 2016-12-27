/*
 * The MIT License
 *
 * Copyright (c) 2016 Adrien Lecharpentier
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.jenkins.support.plugins.injector.http;

import io.jenkins.support.plugins.injector.service.PluginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author Adrien Lecharpentier
 */
@Component
public class Neo4JHealthIndicator extends AbstractHealthIndicator {
    private final PluginService pluginService;

    @Autowired
    public Neo4JHealthIndicator(PluginService pluginService) {
        this.pluginService = pluginService;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        HashMap<String, Long> neo4jStats = new HashMap<>();
        neo4jStats.put("count", pluginService.countUniquePlugins());
        neo4jStats.put("nodes", pluginService.countAllPlugins());
        neo4jStats.put("edges", pluginService.countAllDependencies());
        builder.withDetail("plugins", neo4jStats).up();
    }
}
