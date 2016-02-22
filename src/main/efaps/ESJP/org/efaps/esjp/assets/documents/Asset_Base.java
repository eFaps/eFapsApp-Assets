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

package org.efaps.esjp.assets.documents;

import org.efaps.admin.event.Parameter;
import org.efaps.admin.event.Parameter.ParameterValues;
import org.efaps.admin.event.Return;
import org.efaps.admin.event.Return.ReturnValues;
import org.efaps.admin.program.esjp.EFapsApplication;
import org.efaps.admin.program.esjp.EFapsUUID;
import org.efaps.admin.ui.AbstractCommand;
import org.efaps.db.Insert;
import org.efaps.db.Instance;
import org.efaps.db.PrintQuery;
import org.efaps.db.QueryBuilder;
import org.efaps.esjp.assets.util.Assets;
import org.efaps.esjp.ci.CIAssets;
import org.efaps.esjp.ci.CIFormAssets;
import org.efaps.esjp.ci.CIProducts;
import org.efaps.esjp.common.uiform.Create;
import org.efaps.esjp.erp.Naming;
import org.efaps.util.EFapsException;

/**
 * TODO comment!
 *
 * @author The eFaps Team
 */
@EFapsUUID("799682f6-186c-40ac-a931-55fd3387d869")
@EFapsApplication("eFapsApp-Assets")
public abstract class Asset_Base
    extends AbstractAssetsDocument
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

    /**
     * Access check4 stock product.
     *
     * @param _parameter the _parameter
     * @return the return
     * @throws EFapsException the e faps exception
     */
    public Return accessCheck4StockProduct(final Parameter _parameter)
        throws EFapsException
    {
        final Return ret = new Return();
        final boolean inverse = "true".equalsIgnoreCase(getProperty(_parameter, "Inverse"));

        final QueryBuilder queryBldr = new QueryBuilder(CIAssets.Asset2ProductAbstract);
        queryBldr.addWhereAttrEqValue(CIAssets.Asset2ProductAbstract.ToLinkAbstract, _parameter.getInstance());
        final boolean access = queryBldr.getQuery().executeWithoutAccessCheck().isEmpty();

        if (!inverse && access || inverse && !access) {
            ret.put(ReturnValues.TRUE, true);
        }
        return ret;
    }

    @Override
    public Return getJavaScriptUIValue(final Parameter _parameter)
        throws EFapsException
    {
        final Return retVal;
        final Instance inst = _parameter.getInstance();
        if (inst != null && inst.isValid() && inst.getType().isKindOf(CIProducts.ProductAbstract.getType())) {
            retVal = new Return();

            final PrintQuery print = new PrintQuery(inst);
            print.addAttribute(CIProducts.ProductAbstract.Name, CIProducts.ProductAbstract.Description);
            print.execute();
            final String name = print.getAttribute(CIProducts.ProductAbstract.Name);
            final String desc = print.getAttribute(CIProducts.ProductAbstract.Description);

            final StringBuilder js = new StringBuilder();
            js.append("<script type=\"text/javascript\">\n");
            if (name != null) {
                js.append(getSetFieldValue(0, CIFormAssets.Assets_AssetForm.name.name, name));
            }
            if (name != null) {
                js.append(getSetFieldValue(0, CIFormAssets.Assets_AssetForm.note.name, desc));
            }
            js.append("</script>\n");
            retVal.put(ReturnValues.SNIPLETT, js.toString());
        } else {
            retVal = super.getJavaScriptUIValue(_parameter);
        }
        return retVal;
    }
}
