/*
 * Copyright 2003 - 2012 The eFaps Team
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

import org.efaps.admin.datamodel.Status;
import org.efaps.admin.event.Parameter;
import org.efaps.admin.event.Return;
import org.efaps.admin.program.esjp.EFapsRevision;
import org.efaps.admin.program.esjp.EFapsUUID;
import org.efaps.ci.CIType;
import org.efaps.db.Insert;
import org.efaps.db.Instance;
import org.efaps.esjp.ci.CIAssets;
import org.efaps.esjp.ci.CIFormAssets;
import org.efaps.esjp.sales.document.AbstractProductDocument;
import org.efaps.util.EFapsException;
//import target.generated-sources.efaps.org.efaps.esjp.ci.CIAssets;


/**
 * basic class for documents that do not have prices.
 *
 * @author The eFaps Team
 * @version $Id: AbstractDocument_Base.java 3674 2010-01-28 18:52:35Z jan.moxter$
 */
@EFapsUUID("148fb288-7ca8-45ee-abb7-7c304e76272d")
@EFapsRevision("$Rev$")
public abstract class AbstractAssetsDocument_Base
    extends AbstractProductDocument
{

    /**
     * Method to create the basic Document. The method checks for the Type to be
     * created for every attribute if a related field is in the parameters.
     *
     * @param _parameter Parameter as passed from the eFaps API.
     * @return Instance of the document.
     * @throws EFapsException on error.
     */

    /**
     * Used by the AutoCompleteField used in the select doc form for
     * DeliveryNote.
     *
     * @param _parameter Parameter as passed from the eFaps API.
     * @return map list for auto-complete
     * @throws EFapsException on error.
     */
    public Return autoComplete4Assets(final Parameter _parameter)
        throws EFapsException
    {
        return autoComplete4Doc(_parameter, CIAssets.AssetSimple.uuid, (Status[]) null);
    }

    protected void connectAsset2Document(final Parameter _parameter,
                                         final CreatedDoc _doc,
                                         final CIType _ci)
        throws EFapsException
    {
        final Instance instDocType = Instance.get(_parameter
                        .getParameterValue(CIFormAssets.Assets_MaintenanceRequestForm.assets.name));
        if (instDocType.isValid() && _doc.getInstance().isValid()) {
            final Insert insert = new Insert(_ci);
            insert.add(CIAssets.Assets2DocumentAbstract.FromAbstractLink, instDocType);
            insert.add(CIAssets.Assets2DocumentAbstract.ToAbstractLink, _doc.getInstance());
            insert.execute();
        }
    }
}
