/*
 * Copyright 2003 - 2019 The eFaps Team
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

package org.efaps.esjp.assets.util;

import java.util.UUID;

import org.efaps.admin.common.SystemConfiguration;
import org.efaps.admin.program.esjp.EFapsApplication;
import org.efaps.admin.program.esjp.EFapsUUID;
import org.efaps.api.annotation.EFapsSysConfAttribute;
import org.efaps.api.annotation.EFapsSystemConfiguration;
import org.efaps.esjp.admin.common.systemconfiguration.BooleanSysConfAttribute;
import org.efaps.esjp.admin.common.systemconfiguration.EnumSysConfAttribute;
import org.efaps.esjp.admin.common.systemconfiguration.StringSysConfAttribute;
import org.efaps.esjp.common.jasperreport.StandartReport_Base.JasperMime;
import org.efaps.esjp.sales.util.Sales;
import org.efaps.util.cache.CacheReloadException;

/**
 * @author The eFaps Team
 */
@EFapsUUID("6bf8791a-a1d1-47d1-ac63-7cd93e6df22a")
@EFapsApplication("eFapsApp-Assets")
@EFapsSystemConfiguration("f61b83cf-347e-4c94-b75a-6bf10858923c")
public final class Assets
{
    /** The base. */
    public static final String BASE = "org.efaps.assets.";

    /** Assets-Configuration. */
    public static final UUID SYSCONFUUID = UUID.fromString("f61b83cf-347e-4c94-b75a-6bf10858923c");

    /** See description. */
    @EFapsSysConfAttribute
    public static final BooleanSysConfAttribute ACTIVATE = new BooleanSysConfAttribute()
                    .sysConfUUID(SYSCONFUUID)
                    .key(BASE + "Activate")
                    .description("Main switch to activate/deactivate Assets.");

    /** See description. */
    @EFapsSysConfAttribute
    public static final BooleanSysConfAttribute MAINTOR_ACTIVATE = new BooleanSysConfAttribute()
                    .sysConfUUID(SYSCONFUUID)
                    .key(BASE + "MaintenanceOrder.Activate")
                    .description("Activate Maintenance Order.");

    /** See description. */
    @EFapsSysConfAttribute
    public static final BooleanSysConfAttribute MAINTREQ_ACTIVATE = new BooleanSysConfAttribute()
                    .sysConfUUID(SYSCONFUUID)
                    .key(BASE + "MaintenanceRequest.Activate")
                    .description("Activate Request Order.");

    /** See description. */
    @EFapsSysConfAttribute
    public static final BooleanSysConfAttribute LIFECYCLECOST_ACTIVATE = new BooleanSysConfAttribute()
                    .sysConfUUID(SYSCONFUUID)
                    .key(BASE + "LifecycleCost.Activate")
                    .description("Activate Lifecycle-Cost.");

    /** See description. */
    @EFapsSysConfAttribute
    public static final BooleanSysConfAttribute DEPR_ACTIVATE = new BooleanSysConfAttribute()
                    .sysConfUUID(SYSCONFUUID)
                    .key(BASE + "Depreciation.Activate")
                    .description("Activate the mechanism for Depreciation.");

    /** See description. */
    @EFapsSysConfAttribute
    public static final BooleanSysConfAttribute EVENTSCHED_ACTIVATE = new BooleanSysConfAttribute()
                    .sysConfUUID(SYSCONFUUID)
                    .key(BASE + "EventSchedule.Activate")
                    .description("Activate the mechanism for Event Scheduling.");

    /** See description. */
    @EFapsSysConfAttribute
    public static final BooleanSysConfAttribute ASSET_USENUMBERGEN = new BooleanSysConfAttribute()
                    .sysConfUUID(SYSCONFUUID)
                    .key(BASE + "Asset.UseNumberGenerator4Name")
                    .description("Use a NumberGenerator for Asset creation. (Needs Configuration of NG in Commons).");

    /** See description. */
    @EFapsSysConfAttribute
    public static final BooleanSysConfAttribute ASSET_ACTIVATECLASS = new BooleanSysConfAttribute()
                    .sysConfUUID(SYSCONFUUID)
                    .key(BASE + "Asset.ActivateClassification")
                    .description("Activate the Classification for Assets.");

    /** See description. */
    @EFapsSysConfAttribute
    public static final BooleanSysConfAttribute ASSET_ACTIVATEFILESTRBRWS = new BooleanSysConfAttribute()
                    .sysConfUUID(SYSCONFUUID)
                    .key(BASE + "Asset.ActivateFilesStructurBrowser")
                    .description("Activate FilesS tructurBrowser for Assets.");

    /** See description. */
    @EFapsSysConfAttribute
    public static final BooleanSysConfAttribute ASSET_ACTIVATEFILES = new BooleanSysConfAttribute()
                    .sysConfUUID(SYSCONFUUID)
                    .key(BASE + "Asset.ActivateFiles")
                    .description("Activate Files for Assets.");

    /** See description. */
    @EFapsSysConfAttribute
    public static final BooleanSysConfAttribute ASSET_ACTIVATECONTACTS = new BooleanSysConfAttribute()
                    .sysConfUUID(SYSCONFUUID)
                    .key(BASE + "Asset.ActivateContacts")
                    .description("Activate Contacts for Assets.");

    /** See description. */
    @EFapsSysConfAttribute
    public static final StringSysConfAttribute MAINTEORDER_JASPERREPORT = new StringSysConfAttribute()
                    .sysConfUUID(Sales.SYSCONFUUID)
                    .key(BASE + "MaintenanceOrder.JasperReport")
                    .description("Name of the jasperReport for MaintenanceOrder");

    /** See description. */
    @EFapsSysConfAttribute
    public static final EnumSysConfAttribute<JasperMime> MAINTEORDER_MIME = new EnumSysConfAttribute<JasperMime>()
                    .sysConfUUID(Sales.SYSCONFUUID)
                    .key(BASE + "MaintenanceOrder.Mime")
                    .clazz(JasperMime.class)
                    .description("Mime for the jasperReport for UsageReport");

    /**
     * Singelton.
     */
    private Assets()
    {
    }

    /**
     * @return the SystemConfigruation for Assets
     * @throws CacheReloadException on error
     */
    public static SystemConfiguration getSysConfig()
        throws CacheReloadException
    {
        // Assets-Configuration
        return SystemConfiguration.get(Assets.SYSCONFUUID);
    }
}
