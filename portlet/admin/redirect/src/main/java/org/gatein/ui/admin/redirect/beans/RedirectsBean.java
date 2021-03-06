/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.gatein.ui.admin.redirect.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.portal.config.DataStorage;
import org.exoplatform.portal.config.model.PortalConfig;
import org.exoplatform.portal.config.model.PortalRedirect;
import org.gatein.api.PortalRequest;
import org.gatein.api.site.Site;
import org.gatein.api.site.SiteQuery;
import org.gatein.api.site.SiteType;

@ManagedBean(name = "rdrs")
@ViewScoped
public class RedirectsBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Fetches the full list of sites using GateIn API.
     *
     * @return
     */
    public List<Site> getSites() {
        SiteQuery sq = new SiteQuery.Builder().withSiteTypes(SiteType.SITE).build();
        List<Site> s = PortalRequest.getInstance().getPortal().findSites(sq);
        return s;
    }

    // the selected site on the menu. we will show redirects for it
    private String siteName = null;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     * Returns the list of redirects for the selected site.
     *
     * @return a List of {@link PortalRedirect} objects.
     */
    public List<PortalRedirect> getRedirects() {
        if (siteName == null)
            return new ArrayList<PortalRedirect>();

        // FIXME: Use webui Util.getUIPortal();
        DataStorage ds = (DataStorage) ExoContainerContext.getCurrentContainer().getComponentInstanceOfType(DataStorage.class);

        ArrayList<PortalRedirect> r = new ArrayList<PortalRedirect>();
        try {
            PortalConfig cfg = ds.getPortalConfig(siteName);
            r = cfg.getPortalRedirects();

            // FIXME: getPortalRedirects() should return empty list
            if (r == null) {
                r = new ArrayList<PortalRedirect>();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return r;
    }

}