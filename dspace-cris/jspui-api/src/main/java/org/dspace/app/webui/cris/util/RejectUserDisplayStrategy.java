package org.dspace.app.webui.cris.util;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dspace.app.webui.util.ASimpleDisplayStrategy;
import org.dspace.app.webui.util.UIUtil;
import org.dspace.content.IMetadataValue;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.dspace.eperson.factory.EPersonServiceFactory;

public class RejectUserDisplayStrategy extends ASimpleDisplayStrategy
{

	private String myName;

	/**
	 * log4j category
	 */
    public static final Log log = LogFactory
            .getLog(CrisRefDisplayStrategy.class);

	@Override
	public String getMetadataDisplay(HttpServletRequest hrq, int limit, boolean viewFull, String browseType,
			UUID colIdx, UUID itemid, String field, List<IMetadataValue> metadataArray, boolean disableCrossLinks,
			boolean emph) throws JspException {

		String metadata = "";
		
        if (metadataArray.size() > 0) {
			EPerson eperson;
			String value = metadataArray.get(0).getValue();
			UUID uuid = UUID.fromString(value);
			metadata = value;
			Context context = null;
	
			try {
				context = UIUtil.obtainContext(hrq);
				eperson = EPersonServiceFactory.getInstance().getEPersonService().find(context, uuid);
				metadata = eperson.getFullName() + " &lt;" + eperson.getEmail() + "&gt;";
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
        }
		
		return metadata;
	}

}