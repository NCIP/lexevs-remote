package gov.nih.nci.cacoresdk.installer.portal.init;

//import gov.nih.nci.cagrid.introduce.IntroduceConstants;
//import gov.nih.nci.cagrid.introduce.common.ResourceManager;
//import gov.nih.nci.cagrid.introduce.extension.ExtensionsLoader;
//import gov.nih.nci.cagrid.introduce.portal.help.IntroduceHelp;
//import gov.nih.nci.cagrid.introduce.portal.modification.discovery.NamespaceTypeDiscoveryComponent;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import org.apache.log4j.PropertyConfigurator;
import org.cagrid.grape.ApplicationInitializer;

public class SdkInstallerInitializer implements ApplicationInitializer {
    private static final int HELP_MENU = 4;
    private static final int CONFIG_MENU = 3;

    public void intialize() throws Exception {
        PropertyConfigurator.configure("." + File.separator + "conf" + File.separator
            + "log4j.properties");

//        ExtensionsLoader.getInstance();
        setConfigurationOptions();
        prepareMenus();
    }


    private boolean hasKey(Enumeration keys, String key) {
        while (keys.hasMoreElements()) {
            String testKey = (String) keys.nextElement();
            if (testKey.equals(key)) {
                return true;
            }
        }
        return false;
    }


    private void setConfigurationOptions() {
//        try {
//            if (!hasKey(ResourceManager.getConfigurationPropertyKeys(),
//                IntroduceConstants.NAMESPACE_TYPE_REPLACEMENT_POLICY_PROPERTY)) {
//                ResourceManager.setConfigurationProperty(IntroduceConstants.NAMESPACE_TYPE_REPLACEMENT_POLICY_PROPERTY,
//                    NamespaceTypeDiscoveryComponent.ERROR_POLICY);
//            }
//            ResourceManager.setConfigurationProperty(IntroduceConstants.NAMESPACE_TYPE_REPLACEMENT_POLICY_PROPERTY
//                + ".options", NamespaceTypeDiscoveryComponent.ERROR_POLICY + "," + NamespaceTypeDiscoveryComponent.REPLACE_POLICY
//                + "," + NamespaceTypeDiscoveryComponent.IGNORE_POLICY);
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
    }


    private void prepareMenus() {
//        IntroduceHelp help = new IntroduceHelp();
//        JMenu helpMenu = PortalResourceManager.getInstance().getGridPortal().getJMenuBar().getMenu(HELP_MENU);
//        JMenuItem helpMenuItem = new JMenuItem("Introduce Help", IntroduceLookAndFeel.getHelpIcon());
//        helpMenuItem.setMnemonic(KeyEvent.VK_F1);
//        helpMenuItem.addActionListener(help.getFDisplayHelp());
//        helpMenu.insert(helpMenuItem, 0);
//        JMenuItem updateMenuItem = new JMenuItem("Check for Updates", IntroduceLookAndFeel.getUpdateIcon());
//        updateMenuItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                IntroduceUpdateWizard.showUpdateWizard();
//            }
//        });
//        helpMenu.insert(updateMenuItem, 1);
//
//        JMenu configMenu = PortalResourceManager.getInstance().getGridPortal().getJMenuBar().getMenu(CONFIG_MENU);
//        JMenuItem configMenuItem = new JMenuItem("Preferences", IntroduceLookAndFeel.getPreferencesIcon());
//
//        configMenuItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                PreferencesDialog preferences = new PreferencesDialog(PortalResourceManager.getInstance()
//                    .getGridPortal());
//                // user want to configure preferences....
//                PortalUtils.centerComponent(preferences);
//                preferences.setVisible(true);
//            }
//        });
//        configMenu.insert(configMenuItem, 0);
    }

}
