/*
 * Copyright 2003 - 2010 The eFaps Team
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
 * Revision:        $Rev$
 * Last Changed:    $Date$
 * Last Changed By: $Author$
 */

package org.efaps.esjp.assets.documents;

import org.efaps.admin.event.Parameter;
import org.efaps.admin.event.Return;
import org.efaps.admin.program.esjp.EFapsRevision;
import org.efaps.admin.program.esjp.EFapsUUID;
import org.efaps.db.Insert;
import org.efaps.db.Instance;
import org.efaps.esjp.ci.CIAssets;
import org.efaps.esjp.ci.CIFormAssets;
import org.efaps.util.EFapsException;

/**
 * TODO comment!
 *
 * @author The eFaps Team
 * @version $Id$
 */
@EFapsUUID("3d92d505-3a75-437e-818d-ec1abe052cbe")
@EFapsRevision("$Rev$")
public abstract class MaintenanceOrder_Base
    extends AbstractAssetsDocument
{
        /**
     * @param _parameter Parameter as passed from the eFaps API.
     * @return new Return.
     * @throws EFapsException on error.
     */
    public Return create(final Parameter _parameter)
        throws EFapsException
    {
        final CreatedDoc createdDoc = createDoc(_parameter);
        //createPositions(_parameter, createdDoc);
        //executeProcess(_parameter, createdDoc);
        return new Return();
    }



    @Override
    protected void add2DocCreate(final Parameter _parameter,
                                 final Insert _insert,
                                 final CreatedDoc _createdDoc)
        throws EFapsException
    {
        final Instance asset = Instance.get(_parameter.getParameterValue(CIFormAssets.Assets_MaintenanceRequestForm.assets.name ));
                        //ISales.DocumentStockAbstract.Date.name));
        if (asset.isValid()) {
            _insert.add(CIAssets.MaintenanceOrder.Asset, asset);
            _createdDoc.getValues().put(CIAssets.MaintenanceOrder.Asset.name, asset);
            //insert.add(CISales..Date, date);
            //createdDoc.getValues().put(CISales.DocumentStockAbstract.Date.name, date);
        }
    }
}
