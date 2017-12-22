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

import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.efaps.admin.datamodel.Status;
import org.efaps.admin.datamodel.Type;
import org.efaps.admin.event.Parameter;
import org.efaps.admin.event.Parameter.ParameterValues;
import org.efaps.admin.event.Return;
import org.efaps.admin.event.Return.ReturnValues;
import org.efaps.admin.program.esjp.EFapsApplication;
import org.efaps.admin.program.esjp.EFapsUUID;
import org.efaps.db.Context;
import org.efaps.db.Insert;
import org.efaps.db.Instance;
import org.efaps.db.MultiPrintQuery;
import org.efaps.db.QueryBuilder;
import org.efaps.esjp.ci.CIAssets;
import org.efaps.esjp.ci.CIERP;
import org.efaps.esjp.common.uiform.Create;
import org.efaps.esjp.erp.CommonDocument;
import org.efaps.ui.wicket.util.EFapsKey;
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
                _insert.add(CIAssets.EventSchedule.DefinitionLinkAbstract, Instance.get(eventDefinition));
                _insert.add(CIAssets.EventSchedule.Status, Status.find(CIAssets.EventScheduleStatus.Open));

                add2EventScheduleCreate(_parameter, _insert);
            }

            @Override
            public void connect(final Parameter _parameter, final Instance _instance)
                throws EFapsException
            {
                super.connect(_parameter, _instance);
                connect2EventScheduleCreate(_parameter, _instance);
            }
        }.execute(_parameter);
    }

    /**
     * Add additional attributes for the EventSchedule.
     *
     * @param _parameter passed from eFaps API
     * @param _insert Insert of project.
     * @throws EFapsException on error.
     */
    protected void add2EventScheduleCreate(final Parameter _parameter, final Insert _insert)
        throws EFapsException
    {

    }

    /**
     * Add additional relations for the EventSchedule.
     *
     * @param _parameter passed from eFaps API
     * @param _instance of the project created.
     * @throws EFapsException on error.
     */
    protected void connect2EventScheduleCreate(final Parameter _parameter, final Instance _instance)
        throws EFapsException
    {

    }

    /**
     * Autocomplete for the field used to select a project.
     *
     * @param _parameter Parameter as passed from eFaps
     * @return Return containing map needed for an autocomplete field
     * @throws EFapsException on error
     */
    public Return autoComplete4EventDefinition(final Parameter _parameter)
        throws EFapsException
    {
        final String input = (String) _parameter.get(ParameterValues.OTHERS);
        final Map<?, ?> props = (Map<?, ?>) _parameter.get(ParameterValues.PROPERTIES);
        final Map<String, Map<String, String>> sortMap = new TreeMap<>();
        if (input.length() > 0) {
            final String formatStr = props.containsKey("FormatStr") ? (String) props.get("FormatStr") : "%s - %s";

            final Map<Integer, String> types = analyseProperty(_parameter, "Type");
            for (final Entry<Integer, String> entry : types.entrySet()) {
                final Type type = Type.get(entry.getValue());
                final QueryBuilder queryBldr = new QueryBuilder(type);
                queryBldr.addWhereAttrMatchValue(CIERP.EventDefinitionAbstract.Name, input + "*").setIgnoreCase(true);
                queryBldr.addWhereAttrEqValue(CIERP.EventDefinitionAbstract.Active, true);

                final MultiPrintQuery print = queryBldr.getPrint();
                print.addAttribute(CIERP.EventDefinitionAbstract.Name, CIERP.EventDefinitionAbstract.Description);
                print.execute();
                while (print.next()) {
                    final String name = print.<String>getAttribute(CIAssets.AssetAbstract.Name);
                    final String oid = print.getCurrentInstance().getOid();
                    final Formatter formatter = new Formatter(Context.getThreadContext().getLocale());
                    formatter.format(formatStr, name);
                    final String choice = formatter.toString();
                    formatter.close();
                    final Map<String, String> map = new HashMap<>();
                    map.put(EFapsKey.AUTOCOMPLETE_KEY.getKey(), oid);
                    map.put(EFapsKey.AUTOCOMPLETE_VALUE.getKey(), name);
                    map.put(EFapsKey.AUTOCOMPLETE_CHOICE.getKey(), choice);
                    sortMap.put(choice, map);
                }
            }
        }
        final Return retVal = new Return();
        final List<Map<String, String>> list = new ArrayList<>();
        list.addAll(sortMap.values());
        retVal.put(ReturnValues.VALUES, list);
        return retVal;
    }
}
