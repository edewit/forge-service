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
package org.jboss.forge.service.producer;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.jboss.forge.furnace.Furnace;
import org.jboss.forge.furnace.repositories.AddonRepositoryMode;
import org.jboss.forge.furnace.se.FurnaceFactory;

/**
 * Produces {@link Furnace} instances
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
@ApplicationScoped
public class FurnaceProducer
{
   private Furnace furnace;

   public void setup(File repoDir)
   {
      // Initialize Furnace
      ClassLoader ccl = Thread.currentThread().getContextClassLoader();
      furnace = FurnaceFactory.getInstance(ccl, ccl);
      furnace.addRepository(AddonRepositoryMode.IMMUTABLE, repoDir);
      Future<Furnace> future = furnace.startAsync();
      try
      {
         future.get();
      }
      catch (InterruptedException | ExecutionException e)
      {
         throw new RuntimeException("Furnace failed to start.", e);
      }
   }

   @Produces
   public Furnace getFurnace()
   {
      return furnace;
   }

   @PreDestroy
   public void destroy()
   {
      furnace.stop();
   }
}
