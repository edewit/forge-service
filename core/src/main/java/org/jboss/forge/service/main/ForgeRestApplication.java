/**
 *  Copyright 2005-2015 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.jboss.forge.service.main;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.jboss.forge.service.rest.CommandsResource;
import org.jboss.forge.service.rest.JsonMessageWriter;

@ApplicationPath("/api")
public class ForgeRestApplication extends Application
{
   @Inject
   CommandsResource commandsResource;

   @Override
   public Set<Object> getSingletons()
   {
      return new HashSet<>(Arrays.asList(commandsResource));
   }

   @Override
   public Set<Class<?>> getClasses()
   {
      return new HashSet<>(Arrays.asList(JsonMessageWriter.class));
   }
}
