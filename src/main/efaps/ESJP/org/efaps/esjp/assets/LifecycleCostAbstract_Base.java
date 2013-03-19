/*
 * Copyright 2003 - 2013 The eFaps Team
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


package org.efaps.esjp.assets;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.efaps.admin.common.SystemConfiguration;
import org.efaps.admin.datamodel.Type;
import org.efaps.admin.event.Parameter;
import org.efaps.admin.event.Parameter.ParameterValues;
import org.efaps.admin.program.esjp.EFapsRevision;
import org.efaps.admin.program.esjp.EFapsUUID;
import org.efaps.admin.ui.AbstractCommand;
import org.efaps.db.Context;
import org.efaps.db.Insert;
import org.efaps.db.Instance;
import org.efaps.esjp.ci.CIAssets;
import org.efaps.esjp.ci.CIERP;
import org.efaps.util.EFapsException;


/**
 * TODO comment!
 *
 * @author The eFaps Team
 * @version $Id$
 */
@EFapsUUID("dcb681d5-5eef-4b72-b002-0dbb0361f599")
@EFapsRevision("$Rev$")
public abstract class LifecycleCostAbstract_Base
{

    protected void createCost(final Parameter _parameter)
        throws EFapsException
    {
        // Sales-Configuration
        final Instance baseCurrInst = SystemConfiguration.get(
                        UUID.fromString("c9a1cbc3-fd35-4463-80d2-412422a3802f")).getLink("CurrencyBase");

        final Instance rateCurrInst = _parameter.getParameterValue("rateCurrencyLink") == null
                        ? baseCurrInst
                        : Instance.get(CIERP.Currency.getType(), _parameter.getParameterValue("rateCurrencyLink"));

        final Object[] rateObj = new BigDecimal[] { BigDecimal.ONE, BigDecimal.ONE };
        final BigDecimal rate = ((BigDecimal) rateObj[0]).divide((BigDecimal) rateObj[1], 12,
                        BigDecimal.ROUND_HALF_UP);

        final DecimalFormat format = getFormatInstance();
        final Insert insert = new Insert(getType4CostCreate(_parameter));
        insert.add(CIAssets.LifecycleCostAbstract.AssetLinkAbstract, _parameter.getInstance().getId());
        insert.add(CIAssets.LifecycleCostAbstract.RateCurrencyLink, rateCurrInst.getId());
        insert.add(CIAssets.LifecycleCostAbstract.CurrencyLink, baseCurrInst.getId());
        insert.add(CIAssets.LifecycleCostAbstract.Rate, rateObj);

        final String date = _parameter.getParameterValue(getFieldName4Attribute(_parameter,
                        CIAssets.LifecycleCostAbstract.Date.name));
        if (date != null) {
            insert.add(CIAssets.LifecycleCostAbstract.Date, date);
        }

        final String rateAmountStr = _parameter.getParameterValue(getFieldName4Attribute(_parameter,
                        CIAssets.LifecycleCostAbstract.RateAmount.name));
        if (rateAmountStr != null) {
            try {
                final BigDecimal rateAmount = (BigDecimal) format.parse(rateAmountStr);
                insert.add(CIAssets.LifecycleCostAbstract.RateAmount, rateAmount);
                insert.add(CIAssets.LifecycleCostAbstract.Amount, rateAmount.setScale(8, BigDecimal.ROUND_HALF_UP)
                                .divide(rate, BigDecimal.ROUND_HALF_UP));
            } catch (final ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        insert.execute();
    }


    /**
     * Get the type used to create the new Payment Document.
     * @param _parameter Parameter as passed by the eFaps API
     * @return Type use for the insert
     * @throws EFapsException on error
     */
    protected Type getType4CostCreate(final Parameter _parameter)
        throws EFapsException
    {
        final AbstractCommand command = (AbstractCommand) _parameter.get(ParameterValues.UIOBJECT);
        return command.getTargetCreateType();
    }

    /**
     * @param _parameter Parameter as passed by the eFaps API
     * @param _attributeName attributerName the FieldName is wanted for
     * @return fieldname
     * @throws EFapsException on error
     */
    protected String getFieldName4Attribute(final Parameter _parameter,
                                            final String _attributeName)
        throws EFapsException
    {
        return StringUtils.uncapitalize(_attributeName);
    }


    protected DecimalFormat getFormatInstance()
        throws EFapsException
    {
        final DecimalFormat ret = (DecimalFormat) NumberFormat.getInstance(Context.getThreadContext().getLocale());
        ret.setParseBigDecimal(true);
        return ret;
    }
}
