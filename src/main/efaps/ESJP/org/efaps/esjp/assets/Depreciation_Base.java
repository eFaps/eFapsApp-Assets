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
import org.efaps.admin.event.Return;
import org.efaps.admin.program.esjp.EFapsApplication;
import org.efaps.admin.program.esjp.EFapsUUID;
import org.efaps.esjp.common.AbstractCommon;
import org.efaps.esjp.common.uiform.Create;
import org.efaps.util.EFapsException;

/**
 * The Class Depreciation_Base.
 *
 * @author The eFaps Team
 */
@EFapsUUID("dcb681d5-5eef-4b72-b002-0dbb0361f599")
@EFapsApplication("eFapsApp-Assets")
public abstract class Depreciation_Base
    extends AbstractCommon
{
    /**
     * Creates the Depreciation;
     *
     * @param _parameter Parameter as passed by the eFaps API @return new
     * Return @throws EFapsException on error
     * @return the return
     * @throws EFapsException on error
     */
    public Return create(final Parameter _parameter)
        throws EFapsException
    {
        return new Create().execute(_parameter);
    }
}
