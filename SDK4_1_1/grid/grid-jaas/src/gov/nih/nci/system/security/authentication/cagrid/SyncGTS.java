package gov.nih.nci.system.security.authentication.cagrid;


import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.syncgts.bean.SyncDescription;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SyncGTS
{
	protected static Logger log = LogManager.getLogger(SyncGTS.class.getName());
	
	public SyncGTS(File file, Boolean reSync) throws Exception
	{

		try
		{
			SyncDescription description = (SyncDescription) Utils.deserializeDocument(file.getAbsolutePath(),SyncDescription.class);
			gov.nih.nci.cagrid.syncgts.core.SyncGTS instance = gov.nih.nci.cagrid.syncgts.core.SyncGTS.getInstance();
			if(reSync)
				instance.syncAndResyncInBackground(description, false);
			else
				instance.syncOnce(description);
		} 
		catch (Exception e)
		{
			log.error(e);
			throw e;
		}				
	}

}