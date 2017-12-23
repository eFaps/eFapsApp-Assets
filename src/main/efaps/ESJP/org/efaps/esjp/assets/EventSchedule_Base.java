/*
 * Copyright 2003 - 2017 The eFaps Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.efaps.esjp.assets;

import org.efaps.admin.datamodel.Status;
import org.efaps.admin.event.Parameter;
import org.efaps.admin.event.Return;
import org.efaps.admin.program.esjp.EFapsApplication;
import org.efaps.admin.program.esjp.EFapsUUID;
import org.efaps.db.Insert;
import org.efaps.db.Instance;
import org.efaps.esjp.ci.CIAssets;
import org.efaps.esjp.common.uiform.Create;
import org.efaps.esjp.erp.CommonDocument;
import org.efaps.util.EFapsException;

/**
 * @author The eFaps Team
 */
@EFapsUUID("bf827a6a-1da3-4b0c-b2ca-9769f31ab6b0")
@EFapsApplication("eFapsApp-Assets")
public abstract class EventSchedule_Base
    extends CommonDocument
{
    /**
     * Method top create a new Project.
     *
     * @param _parameter Parameter as passed from the eFaps API
     * @return empty Return
     * @throws EFapsException on error
     */
    public Return create(final Parameter _parameter)
        throws EFapsException
    {
        return new Create()
        {
            @Override
            protected void add2basicInsert(final Parameter _parameter, final Insert _insert)
                throws EFapsException
            {
                final String eventDefinition = _parameter.getParameterValue("eventDefinition");
                _insert.add(CIAssets.EventScheduleAbstract.DefinitionLinkAbstract, Instance.get(eventDefinition));
                _insert.add(CIAssets.EventScheduleAbstract.StatusAbstract,
                                Status.find(CIAssets.EventScheduleStatus.Open));
            }
        }.execute(_parameter);
    }
}
