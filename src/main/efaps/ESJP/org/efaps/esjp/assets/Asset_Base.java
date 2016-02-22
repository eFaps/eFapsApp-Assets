/*
 * Copyright 2003 - 2016 The eFaps Team
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

import org.efaps.admin.event.Parameter;
import org.efaps.admin.event.Parameter.ParameterValues;
import org.efaps.admin.event.Return;
import org.efaps.admin.program.esjp.EFapsApplication;
import org.efaps.admin.program.esjp.EFapsUUID;
import org.efaps.admin.ui.AbstractCommand;
import org.efaps.db.Insert;
import org.efaps.esjp.assets.util.Assets;
import org.efaps.esjp.ci.CIAssets;
import org.efaps.esjp.common.AbstractCommon;
import org.efaps.esjp.common.uiform.Create;
import org.efaps.esjp.erp.Naming;
import org.efaps.util.EFapsException;

/**
 * TODO comment!
 *
 * @author The eFaps Team
 */
@EFapsUUID("1959a1a4-349f-41e1-b8ef-cd3df9a882b8")
@EFapsApplication("eFapsApp-Assets")
public abstract class Asset_Base
    extends AbstractCommon
{

    /**
     * @param _parameter Parameter as passed by the eFaps API @return new
     * Return @throws EFapsException on error
     */
    public Return create(final Parameter _parameter)
        throws EFapsException
    {
        final Create create = new Create()
        {

            @Override
            protected void add2basicInsert(final Parameter _parameter,
                                           final Insert _insert)
                                               throws EFapsException
            {
                super.add2basicInsert(_parameter, _insert);
                if (Assets.ASSETUSENUMBERGEN.get()) {
                    final AbstractCommand command = (AbstractCommand) _parameter.get(ParameterValues.UIOBJECT);
                    _insert.add(CIAssets.AssetAbstract.Name,
                                    new Naming().fromNumberGenerator(_parameter,
                                                    command.getTargetCreateType().getName()));
                }
            }
        };
        return create.execute(_parameter);
    }
}
