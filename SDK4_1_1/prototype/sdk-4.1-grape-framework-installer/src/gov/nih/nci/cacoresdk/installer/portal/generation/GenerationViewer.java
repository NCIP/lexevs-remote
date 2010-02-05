package gov.nih.nci.cacoresdk.installer.portal.generation;

import gov.nih.nci.cacoresdk.installer.common.DeployPropertiesManager;
import gov.nih.nci.cacoresdk.installer.common.SdkInstallerLookAndFeel;
import gov.nih.nci.cagrid.common.portal.PortalLookAndFeel;
import gov.nih.nci.cagrid.common.portal.validation.IconFeedbackPanel;
import gov.nih.nci.cagrid.introduce.common.FileFilters;
import gov.nih.nci.cagrid.introduce.common.ResourceManager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.jgoodies.validation.Severity;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.message.SimpleValidationMessage;
import com.jgoodies.validation.util.DefaultValidationResultModel;
import com.jgoodies.validation.util.ValidationUtils;
import com.jgoodies.validation.view.ValidationComponentUtils;


/**
 * SDK Generation Viewer
 * 
 * @author <A HREF="MAILTO:dumitrud@mail.nih.gov">Dan Dumitru</A>
 * @created November 6, 2008
 */
public class GenerationViewer extends GenerationViewerBaseComponent {
	
	private static final long serialVersionUID = 1L;

    public static final String SCHEMA_DIR = "schema";

    public static final String SDK_INSTALL_DIR = "SDK installation directory";
    public static final String PROJECT_NAME = "Project name";
    public static final String NAMESPACE_PREFIX = "Project namespace prefix";
    public static final String WEBSERVICE_NAME = "Project Webservice name";
    public static final String MODEL_FILE_PATH = "Model file absolute path";
    
    public static final String DB_JNDI_URL = "Database JNDI URL";
    public static final String DB_CONNECTION_URL = "Database Connection URL";
    public static final String DB_USERNAME = "Database Username";
    public static final String DB_PASSWORD = "Database Password";
    public static final String DB_DRIVER = "Database Driver";
    public static final String DB_DIALECT = "Database Dialect";
    
    public static final String CSM_DB_JNDI_URL = "CSM Database JNDI URL";
    public static final String CSM_DB_CONNECTION_URL = "CSM Database Connection URL";
    public static final String CSM_DB_USERNAME = "CSM Database Username";
    public static final String CSM_DB_PASSWORD = "CSM Database Password";
    public static final String CSM_DB_DRIVER = "CSM Database Driver";
    public static final String CSM_DB_DIALECT = "CSM Database Dialect";

    private JPanel mainPanel = null;
    private JPanel buttonPanel = null;
    
    // Buttons
    private JButton previousButton = null;
    private JButton nextButton = null;
    private JButton generateButton = null;
    private JButton sdkInstallDirButton = null;
    private JButton modelFilePathButton = null;
    private JButton closeButton = null;

    private ValidationResultModel validationModel = new DefaultValidationResultModel();

	private JTabbedPane mainTabbedPane = null;
	private int LOGGING_PANEL_INDEX = 5;
	private int CSM_PANEL_INDEX = 7;
	private int CAGRID_AUTH_PANEL_INDEX = 8;

	private JCheckBox lifetimeResource = null;
	private JCheckBox persistantResource = null;
	private JCheckBox notificationResource = null;
	private JCheckBox secureResource = null;
	private JCheckBox resourceProperty = null;
	
	/*
	 * caCore SDK Panel definitions
	 */

	// Primary
	private JPanel projectSettingsPanel = null;
	private JPanel modelSettingsPanel = null;
	private JPanel codeGenSettingsPanel = null;
	private JPanel dbConnectionSettingsPanel = null;
	
	// Writable API
	private JPanel writableApiSettingsPanel = null;
	private JPanel writableApiSettingsSubPanel = null;
	
	// Common Logging (CLM) 
	private JPanel clmSettingsPanel = null;
	private JPanel clmSettingsSubPanel = null;
	
	// Security
	private JPanel securitySettingsPanel = null;
	private JPanel securitySettingsSubPanel = null;
	private JPanel csmDbConnectionSettingsPanel = null;
	private JPanel caGridAuthSettingsPanel = null;

	// App Server
	private JPanel appServerSettingsPanel = null;

	// Advanced
	private JPanel advancedSettingsPanel = null;
	
	/*	
	 * caCore SDK Component definitions
	 */
	
	// Common Label Definitions
	private String blankLine = "---------------------------------------";
    
    // Project Settings Panel Label Definitions
    private JLabel sdkInstallDirLabel = null;
    private JLabel projectNameLabel = null;
    private JLabel namespacePrefixLabel = null;
    private JLabel webserviceNameLabel = null;
    
	//Project Settings Panel Component Definitions
	private JTextField sdkInstallDirField = null;
    private JTextField projectNameField = null;
	private JTextField namespacePrefixField = null;
    private JTextField webserviceNameField = null;
    private boolean hasUserModifiedWebserviceName=false;
    
    //Model Settings Panel Label Definitions
    private JLabel modelFileLabel = null;
    private JLabel modelFileTypeLabel = null;
    private JLabel logicalModelLabel = null;
    private JLabel dataModelLabel = null;
    private JLabel includePackageLabel = null;
    private JLabel excludePackageLabel = null;
    private JLabel excludeNameLabel = null;
    private JLabel excludeNamespaceLabel = null;
    
    //Model Settings Panel Component Definitions
    private JTextField modelFileField = null;
    private JComboBox  modelFileTypeComboBox = null;
    private JTextField logicalModelField = null;
    private JTextField dataModelField = null;
    private JTextField includePackageField = null;
    private JTextField excludePackageField = null;
    private JTextField excludeNameField = null;
    private JTextField excludeNamespaceField = null;
    
    //Code Generation Settings Panel Label Definitions
    private JLabel validateLogicalModelLabel = null;
    private JLabel validateModelMappingLabel = null;
    private JLabel validateGmeTagsLabel = null;
    private JLabel generateHibernateMappingLabel = null;
    private JLabel generateBeansLabel = null;
    private JLabel generateCastorMappingLabel = null;
    private JLabel generateXsdLabel = null;
    private JLabel generateXsdWithGmeTagsLabel = null;
    private JLabel generateXsdWithPermissibleValuesLabel = null;
    private JLabel generateWsddLabel = null;
    private JLabel generateHibernateValidatorLabel = null;
    
	//Code Generation Settings Panel Component Definitions
    private JCheckBox validateLogicalModelCheckBox = null;
    private JCheckBox validateModelMappingCheckBox = null;
    private JCheckBox validateGmeTagsCheckBox = null;
    private JCheckBox generateHibernateMappingCheckBox = null;
    private JCheckBox generateBeansCheckBox = null;
    private JCheckBox generateCastorMappingCheckBox = null;
    private JCheckBox generateXsdCheckBox = null;
    private JCheckBox generateXsdWithGmeTagsCheckBox = null;
    private JCheckBox generateXsdWithPermissibleValuesCheckBox = null;
    private JCheckBox generateWsddCheckBox = null;
    private JCheckBox generateHibernateValidatorCheckBox = null;
    
    //DB Connection Settings Panel Label Definitions
    private JLabel useJndiBasedConnectionLabel = null;
    private JLabel dbJndiUrlLabel = null;
    private JLabel dbConnectionUrlLabel = null;
    private JLabel dbUsernameLabel = null;
    private JLabel dbPasswordLabel = null;
    private JLabel dbDriverLabel = null;
    private JLabel dbDialectLabel = null;
    
	//DB Connection Settings Panel Component Definitions
    private JCheckBox  useJndiBasedConnectionCheckBox = null;
    private JTextField dbJndiUrlField = null;
    private JTextField dbConnectionUrlField = null;
    private JTextField dbUsernameField = null;
    private JTextField dbPasswordField = null;
    private JTextField dbDriverField = null;
    private JTextField dbDialectField = null;
    
    //Writable API Settings Panel Label Definitions
    private JLabel enableWritableApiExtensionLabel = null;
    private JLabel databaseTypeLabel = null;
    private JLabel identityGeneratorTagLabel = null;
    private JLabel caDsrConnectionUrlLabel = null;
    private JLabel enableCommonLoggingModuleLabel = null;
    private JLabel clmProjectNameLabel = null;
    private JLabel clmDbConnectionUrlLabel = null;
    private JLabel clmDbUsernameLabel = null;
    private JLabel clmDbPasswordLabel = null;
    private JLabel clmDbDriverLabel = null;
    
	//Writable API Settings Panel Component Definitions
    private JCheckBox  enableWritableApiExtensionCheckBox = null;
    private JTextField databaseTypeField = null;
    private JTextField identityGeneratorTagField = null;
    private JTextField caDsrConnectionUrlField = null;
    private JCheckBox  enableCommonLoggingModuleCheckBox = null;
    private JTextField clmProjectName = null;
    private JTextField clmDbConnectionUrlField = null;
    private JTextField clmDbUsernameField = null;
    private JTextField clmDbPasswordField = null;
    private JTextField clmDbDriverField = null;
    
    //Security Settings Panel Label Definitions
    private JLabel enableSecurityLabel = null;
    private JLabel enableInstanceLevelSecurityLabel = null;
    private JLabel enableAttributeLevelSecurityLabel = null;
    private JLabel csmProjectNameLabel = null;
    private JLabel cacheProtectionElementsLabel = null;
    
	//Security Settings Panel Component Definitions
    private JCheckBox  enableSecurityCheckBox = null;
    private JCheckBox  enableInstanceLevelSecurityCheckBox = null;
    private JCheckBox  enableAttributeLevelSecurityCheckBox = null;
    private JTextField csmProjectNameField = null;
    private JCheckBox  cacheProtectionElementsCheckBox = null;

    //CSM DB Connection Settings Panel Label Definitions
    private JLabel csmUseJndiBasedConnectionLabel = null;
    private JLabel csmDbJndiUrlLabel = null;
    private JLabel csmDbConnectionUrlLabel = null;
    private JLabel csmDbUsernameLabel = null;
    private JLabel csmDbPasswordLabel = null;
    private JLabel csmDbDriverLabel = null;
    private JLabel csmDbDialectLabel = null;
    
	//CSM DB Connection Settings Panel Component Definitions
    private JCheckBox  csmUseJndiBasedConnectionCheckBox = null;
    private JTextField csmDbJndiUrlField = null;
    private JTextField csmDbConnectionUrlField = null;
    private JTextField csmDbUsernameField = null;
    private JTextField csmDbPasswordField = null;
    private JTextField csmDbDriverField = null;
    private JTextField csmDbDialectField = null;
    
    //caGRID Authentication Settings Panel Label Definitions
    private JLabel enableCaGridLoginModuleLabel = null;
    private JLabel caGridLoginModuleNameLabel = null;
    private JLabel caGridAuthSvcUrlLabel = null;
    private JLabel caGridDorianSvcUrlLabel = null;
    private JLabel enableCsmLoginModuleLabel = null;
    private JLabel sdkGridLoginSvcNameLabel = null;
    private JLabel sdkGridLoginSvcUrlLabel = null;
    
	//caGRID Authentication Settings Panel Component Definitions
    private JCheckBox  enableCaGridLoginModuleCheckBox = null;
    private JTextField caGridLoginModuleNameField = null;
    private JTextField caGridAuthSvcUrlField = null;
    private JTextField caGridDorianSvcUrlField = null;
    private JCheckBox  enableCsmLoginModuleCheckBox = null;
    private JTextField sdkGridLoginSvcNameField = null;
    private JTextField sdkGridLoginSvcUrlField = null;
    	
    //Application Server Settings Panel Label Panel Label Definitions
    private JLabel serverTypeLabel = null;
    private JLabel serverUrlLabel = null;
    
	//Application Server Settings Panel Label Panel Component Definitions
    private JComboBox   serverTypeComboBox = null;
    private JTextField  serverUrlField = null;
    
    //Advanced Settings Panel Label Panel Label Definitions
    private JLabel cachePathLabel = null;
    
	//Advanced Settings Panel Label Panel Component Definitions
    private JTextField  cachePathField = null;

    
    //TODO
    
    public GenerationViewer() {
        super();
        initialize();
    }


    /**
     * This method initializes this Viewer
     */
    private void initialize() {
        this.setContentPane(getMainPanel());
        this.setFrameIcon(SdkInstallerLookAndFeel.getGenerateApplicationIcon());
        this.setTitle("Generate an SDK Application");

        initValidation();
    }


    private void initValidation() {
        ValidationComponentUtils.setMessageKey(getSdkInstallDirField(), SDK_INSTALL_DIR);
        ValidationComponentUtils.setMessageKey(getProjectNameField(), PROJECT_NAME);
        ValidationComponentUtils.setMessageKey(getNamespacePrefixField(), NAMESPACE_PREFIX);
        ValidationComponentUtils.setMessageKey(getWebServiceNameField(), WEBSERVICE_NAME);
        
        ValidationComponentUtils.setMessageKey(getModelFileField(), MODEL_FILE_PATH);
        ValidationComponentUtils.setMandatory(getModelFileField(), true);
        
        ValidationComponentUtils.setMessageKey(getDbJndiUrlField(), DB_JNDI_URL);
        ValidationComponentUtils.setMessageKey(getDbConnectionUrlField(), DB_CONNECTION_URL);
        ValidationComponentUtils.setMessageKey(getDbUsernameField(), DB_USERNAME);
        ValidationComponentUtils.setMessageKey(getDbPasswordField(), DB_PASSWORD);
        ValidationComponentUtils.setMessageKey(getDbDriverField(), DB_DRIVER);
        ValidationComponentUtils.setMessageKey(getDbDialectField(), DB_DIALECT);

        //TODO - Add Writable API components
        //TODO - Add Security components

        ValidationComponentUtils.setMessageKey(getCsmDbJndiUrlField(), CSM_DB_JNDI_URL);
        ValidationComponentUtils.setMessageKey(getCsmDbConnectionUrlField(), CSM_DB_CONNECTION_URL);
        ValidationComponentUtils.setMessageKey(getCsmDbUsernameField(), CSM_DB_USERNAME);
        ValidationComponentUtils.setMessageKey(getCsmDbPasswordField(), CSM_DB_PASSWORD);
        ValidationComponentUtils.setMessageKey(getCsmDbDriverField(), CSM_DB_DRIVER);
        ValidationComponentUtils.setMessageKey(getCsmDbDialectField(), CSM_DB_DIALECT);
        
        //TODO - Add caGrid Auth components 
        //TODO - Add Application Server Components
        //TODO - Advanced Settings components

        toggleDbConnectionFields();
        toggleWritableApiFields();
        toggleSecurityFields();
        toggleCsmDbConnectionFields();
        toggleCaGridLoginFields();
        toggleCsmLoginFields();
        
        validateInput();
        
        updateComponentTreeSeverity();
    }


    private final class FocusChangeHandler implements FocusListener {

        public void focusGained(FocusEvent e) {
            update();

        }


        public void focusLost(FocusEvent e) {
            update();
        }


        private void update() {
            //updateModel();
            validateInput();
        }
    }


    private void validateInput() {

        ValidationResult result = new ValidationResult();

        if (!ValidationUtils.isNotBlank(this.getSdkInstallDirField().getText())) {
            result.add(new SimpleValidationMessage(SDK_INSTALL_DIR + " must not be blank.", Severity.ERROR, SDK_INSTALL_DIR));
        } else {
            File file = new File(this.getSdkInstallDirField().getText());
            if(!file.exists()){
                result.add(new SimpleValidationMessage(SDK_INSTALL_DIR + " does not exist.  Please select the Home directory of an existing SDK Installation.", Severity.ERROR, SDK_INSTALL_DIR));
            }
        }
        
        if (!ValidationUtils.isNotBlank(this.getModelFileField().getText())) {
            result.add(new SimpleValidationMessage(MODEL_FILE_PATH + " must not be blank.", Severity.ERROR, MODEL_FILE_PATH));
        } else {
            File file = new File(this.getModelFileField().getText());
            if(!file.exists()){
                result.add(new SimpleValidationMessage(MODEL_FILE_PATH + " does not exist.  Please select an existing Model file.", Severity.ERROR, MODEL_FILE_PATH));
            }
        }
        	
        //DB Connection Setting Validation
        if (!getUseJndiBasedConnectionCheckBox().isSelected()){

        	if (!ValidationUtils.isNotBlank(this.getDbConnectionUrlField().getText())) {
        		result.add(new SimpleValidationMessage(DB_CONNECTION_URL + " must not be blank.", Severity.ERROR, DB_CONNECTION_URL));
        	}
        	
        	if (!ValidationUtils.isNotBlank(this.getDbUsernameField().getText())) {
        		result.add(new SimpleValidationMessage(DB_USERNAME + " must not be blank.", Severity.ERROR, DB_USERNAME));
        	} 
        	
        	if (!ValidationUtils.isNotBlank(this.getDbPasswordField().getText())) {
        		result.add(new SimpleValidationMessage(DB_PASSWORD + " must not be blank.", Severity.ERROR, DB_PASSWORD));
        	} 
        	
        	if (!ValidationUtils.isNotBlank(this.getDbDriverField().getText())) {
        		result.add(new SimpleValidationMessage(DB_DRIVER + " must not be blank.", Severity.ERROR, DB_DRIVER));
        	} 
        	
        	if (!ValidationUtils.isNotBlank(this.getDbDialectField().getText())) {
        		result.add(new SimpleValidationMessage(DB_DIALECT + " must not be blank.", Severity.ERROR, DB_DIALECT));
        	}
        } else {
        	if (!ValidationUtils.isNotBlank(this.getDbJndiUrlField().getText())) {
        		result.add(new SimpleValidationMessage(DB_JNDI_URL + " must not be blank.", Severity.ERROR, DB_JNDI_URL));
        	}
        }
        
        if (getEnableSecurityCheckBox().isSelected()){
        	//CSM DB Connection Setting Validation
        	if (!getCsmUseJndiBasedConnectionCheckBox().isSelected()){

        		if (!ValidationUtils.isNotBlank(this.getCsmDbConnectionUrlField().getText())) {
        			result.add(new SimpleValidationMessage(CSM_DB_CONNECTION_URL + " must not be blank.", Severity.ERROR, CSM_DB_CONNECTION_URL));
        		}

        		if (!ValidationUtils.isNotBlank(this.getCsmDbUsernameField().getText())) {
        			result.add(new SimpleValidationMessage(CSM_DB_USERNAME + " must not be blank.", Severity.ERROR, CSM_DB_USERNAME));
        		} 

        		if (!ValidationUtils.isNotBlank(this.getCsmDbPasswordField().getText())) {
        			result.add(new SimpleValidationMessage(CSM_DB_PASSWORD + " must not be blank.", Severity.ERROR, CSM_DB_PASSWORD));
        		} 

        		if (!ValidationUtils.isNotBlank(this.getCsmDbDriverField().getText())) {
        			result.add(new SimpleValidationMessage(CSM_DB_DRIVER + " must not be blank.", Severity.ERROR, CSM_DB_DRIVER));
        		} 

        		if (!ValidationUtils.isNotBlank(this.getCsmDbDialectField().getText())) {
        			result.add(new SimpleValidationMessage(CSM_DB_DIALECT + " must not be blank.", Severity.ERROR, CSM_DB_DIALECT));
        		}
        	} else {
        		if (!ValidationUtils.isNotBlank(this.getCsmDbJndiUrlField().getText())) {
        			result.add(new SimpleValidationMessage(CSM_DB_JNDI_URL + " must not be blank.", Severity.ERROR, CSM_DB_JNDI_URL));
        		}
        	}
        }

        this.validationModel.setResult(result);

        updateComponentTreeSeverity();
        updateGenerateButton();
        updatePreviousButton();
        updateNextButton();
    }


    private void updateComponentTreeSeverity() {
        ValidationComponentUtils.updateComponentTreeMandatoryAndBlankBackground(this);
        ValidationComponentUtils.updateComponentTreeSeverityBackground(this, this.validationModel.getResult());
    }


    private void updateGenerateButton() {
        if (this.validationModel.hasErrors()) {
            this.getGenerateButton().setEnabled(false);
        } else {
            this.getGenerateButton().setEnabled(true);
        }
    }
    
    private void updatePreviousButton() {
    	if (mainTabbedPane.getSelectedIndex() <= 0){
            this.getPreviousButton().setEnabled(false);
        } else {
            this.getPreviousButton().setEnabled(true);
        }
    }
    
    private void updateNextButton() {
    	if (mainTabbedPane.getSelectedIndex() >= mainTabbedPane.getTabCount()-1){
            this.getNextButton().setEnabled(false);
        } else {
            this.getNextButton().setEnabled(true);
        }
    }

    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getMainPanel() {
        if (mainPanel == null) {
            GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
            gridBagConstraints111.fill = GridBagConstraints.BOTH;
            gridBagConstraints111.weighty = 1.0;
            gridBagConstraints111.weightx = 1.0;
            
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.insets = new java.awt.Insets(5, 5, 5, 5);
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.gridy = 2;
            gridBagConstraints1.anchor = java.awt.GridBagConstraints.SOUTH;
            gridBagConstraints1.gridheight = 1;
            
            mainPanel = new JPanel();
            mainPanel.setLayout(new GridBagLayout());
            mainPanel.add(getButtonPanel(), gridBagConstraints1);
            mainPanel.add(getMainTabbedPane(), gridBagConstraints111);
        }
        return mainPanel;
    }


    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getPreviousButton(), null);
            buttonPanel.add(getNextButton(), null);
            buttonPanel.add(getGenerateButton(), null);
            buttonPanel.add(getCloseButton(), null);
        }
        return buttonPanel;
    }

    /**
     * This method initializes the Previous jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getPreviousButton() {
        if (previousButton == null) {
        	previousButton = new JButton();
        	previousButton.setText("Previous");
        	previousButton.setIcon(SdkInstallerLookAndFeel.getPreviousIcon());
        	previousButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    mainTabbedPane.setSelectedIndex(getPreviousIndex());
                    validateInput();
                }
            });
        }

        return previousButton;
    }
    
    /**
     * This method initializes the Previous jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getNextButton() {
        if (nextButton == null) {
        	nextButton = new JButton();
        	nextButton.setText("Next");
        	nextButton.setIcon(SdkInstallerLookAndFeel.getNextIcon());
        	nextButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                	 mainTabbedPane.setSelectedIndex(getNextIndex());
                	 validateInput();
                }
            });
        }

        return nextButton;
    }
    
    private int getPreviousIndex(){
    	int index = mainTabbedPane.getSelectedIndex();

    	while (index >= 0){
    		--index;
    		
    		if (mainTabbedPane.isEnabledAt(index))
    			return index;
    	}
    	
    	return 0;
    }
    
    private int getNextIndex(){
    	int index = mainTabbedPane.getSelectedIndex();

    	while (index <= mainTabbedPane.getTabCount()-1){
    		++index;
    		
    		if (mainTabbedPane.isEnabledAt(index))
    			return index;
    	}
    	
    	return mainTabbedPane.getTabCount() - 1;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getGenerateButton() {
        if (generateButton == null) {
            generateButton = new JButton();
            generateButton.setText("Generate");
            generateButton.setIcon(SdkInstallerLookAndFeel.getGenerateApplicationIcon());
            generateButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    generateApplication(getSdkInstallDirField().getText(),getInstallerPropsMap()); 
                }
            });
        }

        return generateButton;
    }

    /**
     * This method initializes projectNameField
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getProjectNameField() {
        if (projectNameField == null) {
            projectNameField = new JTextField();
            projectNameField.setText(DeployPropertiesManager.getDeployPropertyValue("PROJECT_NAME"));
            projectNameField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    updateSuggestedWebserviceName();
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    updateSuggestedWebserviceName();
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    updateSuggestedWebserviceName();
                    validateInput();
                }
            });
            projectNameField.addFocusListener(new FocusChangeHandler());
        }
        return projectNameField;
    }


    protected void updateSuggestedWebserviceName() {
    	if (!hasUserModifiedWebserviceName)
    		getWebServiceNameField().setText(getProjectNameField().getText()+"Service");
    }
    

    protected void toggleDbConnectionFields() {
		if (useJndiBasedConnectionCheckBox.isSelected()){
			dbJndiUrlField.setEnabled(true);
			
			dbConnectionUrlField.setEnabled(false);
			dbUsernameField.setEnabled(false);
			dbPasswordField.setEnabled(false);
			dbDriverField.setEnabled(false);
			dbDialectField.setEnabled(false);
		} else{
			dbJndiUrlField.setEnabled(false);
			
			dbConnectionUrlField.setEnabled(true);
			dbUsernameField.setEnabled(true);
			dbPasswordField.setEnabled(true);
			dbDriverField.setEnabled(true);
			dbDialectField.setEnabled(true);
		}
    }
    
    protected void toggleWritableApiFields() {
		if (enableWritableApiExtensionCheckBox.isSelected()){
			databaseTypeField.setEnabled(true);
			identityGeneratorTagField.setEnabled(true);
			caDsrConnectionUrlField.setEnabled(true);
			
			mainTabbedPane.setEnabledAt(LOGGING_PANEL_INDEX, true); // CLM Panel
			enableCommonLoggingModuleCheckBox.setEnabled(true);
			
			if (enableCommonLoggingModuleCheckBox.isSelected()){
				clmProjectName.setEnabled(true);
				clmDbConnectionUrlField.setEnabled(true);
				clmDbUsernameField.setEnabled(true);
				clmDbPasswordField.setEnabled(true);
				clmDbDriverField.setEnabled(true);
			} else {
				clmProjectName.setEnabled(false);
				clmDbConnectionUrlField.setEnabled(false);
				clmDbUsernameField.setEnabled(false);
				clmDbPasswordField.setEnabled(false);
				clmDbDriverField.setEnabled(false);
			}
		} else{
			databaseTypeField.setEnabled(false);
			identityGeneratorTagField.setEnabled(false);
			caDsrConnectionUrlField.setEnabled(false);
			
			mainTabbedPane.setEnabledAt(LOGGING_PANEL_INDEX, false); // CLM Panel
			
			enableCommonLoggingModuleCheckBox.setEnabled(false);
			clmProjectName.setEnabled(false);
			clmDbConnectionUrlField.setEnabled(false);
			clmDbUsernameField.setEnabled(false);
			clmDbPasswordField.setEnabled(false);
			clmDbDriverField.setEnabled(false);
		}
    }
    
    protected void toggleSecurityFields() {
		if (enableSecurityCheckBox.isSelected()){
		    enableInstanceLevelSecurityCheckBox.setEnabled(true);
		    enableAttributeLevelSecurityCheckBox.setEnabled(true);
		    csmProjectNameField.setEnabled(true);
		    cacheProtectionElementsCheckBox.setEnabled(true);

		    mainTabbedPane.setEnabledAt(CSM_PANEL_INDEX, true); // CSM Panel
		    mainTabbedPane.setEnabledAt(CAGRID_AUTH_PANEL_INDEX, true); // caGrid Auth Panel
		} else{
		    enableInstanceLevelSecurityCheckBox.setEnabled(false);
		    enableAttributeLevelSecurityCheckBox.setEnabled(false);
		    csmProjectNameField.setEnabled(false);
		    cacheProtectionElementsCheckBox.setEnabled(false);

		    mainTabbedPane.setEnabledAt(CSM_PANEL_INDEX, false); // CSM Panel
		    mainTabbedPane.setEnabledAt(CAGRID_AUTH_PANEL_INDEX, false); // caGrid Auth Panel
		}
    }
    
    protected void toggleCsmDbConnectionFields() {
		if (csmUseJndiBasedConnectionCheckBox.isSelected()){
			csmDbJndiUrlField.setEnabled(true);
			
			csmDbConnectionUrlField.setEnabled(false);
			csmDbUsernameField.setEnabled(false);
			csmDbPasswordField.setEnabled(false);
			csmDbDriverField.setEnabled(false);
			csmDbDialectField.setEnabled(false);
		} else{
			csmDbJndiUrlField.setEnabled(false);
			
			csmDbConnectionUrlField.setEnabled(true);
			csmDbUsernameField.setEnabled(true);
			csmDbPasswordField.setEnabled(true);
			csmDbDriverField.setEnabled(true);
			csmDbDialectField.setEnabled(true);
		}
    }
    
    protected void toggleCaGridLoginFields() {
		if (enableCaGridLoginModuleCheckBox.isSelected()){
			caGridLoginModuleNameField.setEnabled(true);
			caGridAuthSvcUrlField.setEnabled(true);
			caGridDorianSvcUrlField.setEnabled(true);
		} else{
			caGridLoginModuleNameField.setEnabled(false);
			caGridAuthSvcUrlField.setEnabled(false);
			caGridDorianSvcUrlField.setEnabled(false);
		}
    }
    
    protected void toggleCsmLoginFields() {
		if (enableCsmLoginModuleCheckBox.isSelected()){
			sdkGridLoginSvcNameField.setEnabled(true);
			sdkGridLoginSvcUrlField.setEnabled(true);
		} else{
			sdkGridLoginSvcNameField.setEnabled(false);
			sdkGridLoginSvcUrlField.setEnabled(false);
		}
    }

    /**
     * This method initializes jTextField
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getSdkInstallDirField() {
        if (sdkInstallDirField == null) {
            sdkInstallDirField = new JTextField();
//            sdkInstallDirField.setText(home + File.separator
//                + IntroduceEnginePropertiesManager.getIntroducePropertyValue(IntroduceConstants.DEFAULT_SERVICE_NAME));
            sdkInstallDirField.setText("");
            sdkInstallDirField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }


                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
            sdkInstallDirField.addFocusListener(new FocusChangeHandler());
        }
        return sdkInstallDirField;
    }


    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getSdkInstallDirButton() {
        if (sdkInstallDirButton == null) {
            sdkInstallDirButton = new JButton();
            sdkInstallDirButton.setText("Browse");
            sdkInstallDirButton.setIcon(SdkInstallerLookAndFeel.getBrowseIcon());
            sdkInstallDirButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    try {
                        String previous = getSdkInstallDirField().getText();
                        String location = ResourceManager.promptDir(previous);
                        if (location != null && location.length() > 0) {
                            getSdkInstallDirField().setText(location);
                        } else {
                            getSdkInstallDirField().setText(previous);
                        }
                        validateInput();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
        return sdkInstallDirButton;
    }
    
    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getModelFilePathButton() {
        if (modelFilePathButton == null) {
        	modelFilePathButton = new JButton();
        	modelFilePathButton.setText("Browse");
        	modelFilePathButton.setIcon(SdkInstallerLookAndFeel.getBrowseIcon());
        	modelFilePathButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    try {
                        String previous = getModelFileField().getText();
                        String location = ResourceManager.promptFile(previous, FileFilters.XMI_FILTER);
                        if (location != null && location.length() > 0) {
                        	getModelFileField().setText(location);
                        } else {
                        	getModelFileField().setText(previous);
                        }
                        validateInput();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
        return modelFilePathButton;
    }


    /**
     * This method initializes Project Namespace Prefix Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getNamespacePrefixField() {
        if (namespacePrefixField == null) {
            namespacePrefixField = new JTextField();
            namespacePrefixField.setText(DeployPropertiesManager.getDeployPropertyValue("NAMESPACE_PREFIX"));
            namespacePrefixField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
            namespacePrefixField.addFocusListener(new FocusChangeHandler());
        }
        return namespacePrefixField;
    }


    /**
     * This method initializes webserviceNameField
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getWebServiceNameField() {
        if (webserviceNameField == null) {
            webserviceNameField = new JTextField();
            //webserviceNameField.setText(DeployPropertiesManager.getDeployPropertyValue("WEBSERVICE_NAME"));
            webserviceNameField.setText(DeployPropertiesManager.getDeployPropertyValue("PROJECT_NAME")+"Service");
            webserviceNameField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) { 
                	if (isFocusOwner())
                		hasUserModifiedWebserviceName=true;
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                	if (isFocusOwner())
                		hasUserModifiedWebserviceName=true;
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                	if (isFocusOwner())
                		hasUserModifiedWebserviceName=true;
                    validateInput();
                }
            });
            webserviceNameField.addFocusListener(new FocusChangeHandler());
        }
        return webserviceNameField;
    }
    
    /**
     * This method initializes Model File Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getModelFileField() {
        if (modelFileField == null) {
        	modelFileField = new JTextField();
        	//modelFileField.setText(DeployPropertiesManager.getDeployPropertyValue("MODEL_FILE"));
        	modelFileField.setText("");
        	modelFileField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	modelFileField.addFocusListener(new FocusChangeHandler());
        }
        return modelFileField;
    }
    
    /**
     * This method initializes Model File Type Combo Box
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getModelFileTypeField() {
        if (modelFileTypeComboBox == null) {
        	modelFileTypeComboBox = new JComboBox();
        	modelFileTypeComboBox.addItem("EA");
        	modelFileTypeComboBox.addItem("ARGO");

            String modelFileType = DeployPropertiesManager.getDeployPropertyValue("MODEL_FILE_TYPE");
            modelFileTypeComboBox.setSelectedItem(modelFileType);

//        	modelFileTypeComboBox.getDocument().addDocumentListener(new DocumentListener() {
//                public void changedUpdate(DocumentEvent e) {
//                    validateInput();
//                }
//
//                public void removeUpdate(DocumentEvent e) {
//                    validateInput();
//                }
//
//                public void insertUpdate(DocumentEvent e) {
//                    validateInput();
//                }
//            });
        	modelFileTypeComboBox.addFocusListener(new FocusChangeHandler());
        }
        return modelFileTypeComboBox;
    }
    
    /**
     * This method initializes Logical Model Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getLogicalModelField() {
        if (logicalModelField == null) {
        	logicalModelField = new JTextField();
        	logicalModelField.setText(DeployPropertiesManager.getDeployPropertyValue("LOGICAL_MODEL"));
        	logicalModelField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	logicalModelField.addFocusListener(new FocusChangeHandler());
        }
        return logicalModelField;
    }
    
    /**
     * This method initializes Data Model Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getDataModelField() {
        if (dataModelField == null) {
        	dataModelField = new JTextField();
        	dataModelField.setText(DeployPropertiesManager.getDeployPropertyValue("DATA_MODEL"));
        	dataModelField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	dataModelField.addFocusListener(new FocusChangeHandler());
        }
        return dataModelField;
    }
    
    /**
     * This method initializes the Include Package Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getIncludePackageField() {
        if (includePackageField == null) {
        	includePackageField = new JTextField();
        	includePackageField.setText(DeployPropertiesManager.getDeployPropertyValue("INCLUDE_PACKAGE"));
        	includePackageField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	includePackageField.addFocusListener(new FocusChangeHandler());
        }
        return includePackageField;
    }
    
    /**
     * This method initializes the Exclude Package Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getExcludePackageField() {
        if (excludePackageField == null) {
        	excludePackageField = new JTextField();
        	excludePackageField.setText(DeployPropertiesManager.getDeployPropertyValue("EXCLUDE_PACKAGE"));
        	excludePackageField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	excludePackageField.addFocusListener(new FocusChangeHandler());
        }
        return excludePackageField;
    }
    
    /**
     * This method initializes the Exclude Name Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getExcludeNameField() {
        if (excludeNameField == null) {
        	excludeNameField = new JTextField();
        	excludeNameField.setText(DeployPropertiesManager.getDeployPropertyValue("EXCLUDE_NAME"));
        	excludeNameField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	excludeNameField.addFocusListener(new FocusChangeHandler());
        }
        return excludeNameField;
    }

    /**
     * This method initializes the Exclude Namespace Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getExcludeNamespaceField() {
        if (excludeNamespaceField == null) {
        	excludeNamespaceField = new JTextField();
        	excludeNamespaceField.setText(DeployPropertiesManager.getDeployPropertyValue("EXCLUDE_NAMESPACE"));
        	excludeNamespaceField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	excludeNamespaceField.addFocusListener(new FocusChangeHandler());
        }
        return excludeNamespaceField;
    }
    
    /**
     * This method initializes the Validate Logical Model Check Box
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getValidateLogicalModelCheckBox() {
        if (validateLogicalModelCheckBox == null) {
        	validateLogicalModelCheckBox = new JCheckBox();
        	validateLogicalModelCheckBox.setToolTipText("Validate the Logical Model prior to generating the application?");
        	validateLogicalModelCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	validateLogicalModelCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("VALIDATE_LOGICAL_MODEL")));
        	validateLogicalModelCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	validateLogicalModelCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//e.g.: checkResourcePropertyOptions();
				}
        	});

        	validateLogicalModelCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return validateLogicalModelCheckBox;
    }
    
    /**
     * This method initializes the Validate Model Mapping Check Box
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getValidateModelMappingCheckBox() {
        if (validateModelMappingCheckBox == null) {
        	validateModelMappingCheckBox = new JCheckBox();
        	validateModelMappingCheckBox.setToolTipText("Validate the Model Mapping prior to generating the application?");
        	validateModelMappingCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	validateModelMappingCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("VALIDATE_MODEL_MAPPING")));
        	validateModelMappingCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	validateModelMappingCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					;//e.g.: checkResourcePropertyOptions();
				}
        	});

        	validateModelMappingCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return validateModelMappingCheckBox;
    }
    
    /**
     * This method initializes the Validate Model Mapping Check Box
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getValidateGmeTagsCheckBox() {
        if (validateGmeTagsCheckBox == null) {
        	validateGmeTagsCheckBox = new JCheckBox();
        	validateGmeTagsCheckBox.setToolTipText("Validate GME tags in the logical model prior to generating the application?");
        	validateGmeTagsCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	validateGmeTagsCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("VALIDATE_GME_TAGS")));
        	validateGmeTagsCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	validateGmeTagsCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					;//e.g.: checkResourcePropertyOptions();
				}
        	});

        	validateGmeTagsCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return validateGmeTagsCheckBox;
    } 
    
    /**
     * This method initializes the Generate Beans Check Box
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getGenerateBeansCheckBox() {
        if (generateBeansCheckBox == null) {
        	generateBeansCheckBox = new JCheckBox();
        	generateBeansCheckBox.setToolTipText("Generate domain Java Beans?");
        	generateBeansCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	generateBeansCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("GENERATE_BEANS")));
        	generateBeansCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	generateBeansCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					;//e.g.: checkResourcePropertyOptions();
				}
        	});

        	generateBeansCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return generateBeansCheckBox;
    } 
    
    /**
     * This method initializes the Generate Hibernate Mapping File Check Box
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getGenerateHibernateMappingCheckBox() {
        if (generateHibernateMappingCheckBox == null) {
        	generateHibernateMappingCheckBox = new JCheckBox();
        	generateHibernateMappingCheckBox.setToolTipText("Generate Hibernate mapping files?");
        	generateHibernateMappingCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	generateHibernateMappingCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("GENERATE_HIBERNATE_MAPPING")));
        	generateHibernateMappingCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	generateHibernateMappingCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					;//e.g.: checkResourcePropertyOptions();
				}
        	});

        	generateHibernateMappingCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return generateHibernateMappingCheckBox;
    } 
    
    /**
     * This method initializes the Generate Castor Mapping File Check Box
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getGenerateCastorMappingCheckBox() {
        if (generateCastorMappingCheckBox == null) {
        	generateCastorMappingCheckBox = new JCheckBox();
        	generateCastorMappingCheckBox.setToolTipText("Generate Castor mapping files?");
        	generateCastorMappingCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	generateCastorMappingCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("GENERATE_CASTOR_MAPPING")));
        	generateCastorMappingCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	generateCastorMappingCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					;//e.g.: checkResourcePropertyOptions();
				}
        	});

        	generateCastorMappingCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return generateCastorMappingCheckBox;
    }
    
    /**
     * This method initializes the Generate XSD Schema Files Check Box
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getGenerateXsdCheckBox() {
        if (generateXsdCheckBox == null) {
        	generateXsdCheckBox = new JCheckBox();
        	generateXsdCheckBox.setToolTipText("Generate Castor mapping files?");
        	generateXsdCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	generateXsdCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("GENERATE_XSD")));
        	generateXsdCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	generateXsdCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					;//e.g.: checkResourcePropertyOptions();
				}
        	});

        	generateXsdCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return generateXsdCheckBox;
    }
    
    /**
     * This method initializes the Generate XSD Files Check Box
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox generateXsdWithGmeTagsCheckBox() {
        if (generateXsdWithGmeTagsCheckBox == null) {
        	generateXsdWithGmeTagsCheckBox = new JCheckBox();
        	generateXsdWithGmeTagsCheckBox.setToolTipText("Generate XSD files?");
        	generateXsdWithGmeTagsCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	generateXsdWithGmeTagsCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("GENERATE_XSD_WITH_GME_TAGS")));
        	generateXsdWithGmeTagsCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	generateXsdWithGmeTagsCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					;//e.g.: checkResourcePropertyOptions();
				}
        	});

        	generateXsdWithGmeTagsCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return generateXsdWithGmeTagsCheckBox;
    }
    
    /**
     * This method initializes the Generate XSD with Permissible Values Check Box
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getGenerateXsdWithPermissibleValuesCheckBox() {
        if (generateXsdWithPermissibleValuesCheckBox == null) {
        	generateXsdWithPermissibleValuesCheckBox = new JCheckBox();
        	generateXsdWithPermissibleValuesCheckBox.setToolTipText("Generate XSD files using Permissible Values?");
        	generateXsdWithPermissibleValuesCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	generateXsdWithPermissibleValuesCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("GENERATE_XSD_WITH_PERMISSIBLE_VALUES")));
        	generateXsdWithPermissibleValuesCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	generateXsdWithPermissibleValuesCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					;//e.g.: checkResourcePropertyOptions();
				}
        	});

        	generateXsdWithPermissibleValuesCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return generateXsdWithPermissibleValuesCheckBox;
    }
    
    /**
     * This method initializes the Generate XSD with Permissible Values Check Box
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getGenerateWsddLabelCheckBox() {
        if (generateWsddCheckBox == null) {
        	generateWsddCheckBox = new JCheckBox();
        	generateWsddCheckBox.setToolTipText("Generate XSD files?");
        	generateWsddCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	generateWsddCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("GENERATE_WSDD")));
        	generateWsddCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	generateWsddCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					;//e.g.: checkResourcePropertyOptions();
				}
        	});

        	generateWsddCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return generateWsddCheckBox;
    }
    
    /**
     * This method initializes the Generate XSD with Permissible Values Check Box
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getGenerateHibernateValidatorCheckBox() {
        if (generateHibernateValidatorCheckBox == null) {
        	generateHibernateValidatorCheckBox = new JCheckBox();
        	generateHibernateValidatorCheckBox.setToolTipText("Generate Hibernate Validator?");
        	generateHibernateValidatorCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	generateHibernateValidatorCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("GENERATE_HIBERNATE_VALIDATOR")));
        	generateHibernateValidatorCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	generateHibernateValidatorCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					;//e.g.: checkResourcePropertyOptions();
				}
        	});

        	generateHibernateValidatorCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return generateHibernateValidatorCheckBox;
    }
    
    /**
     * This method initializes the Use JNDI Based Connection Check Box
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getUseJndiBasedConnectionCheckBox() {
        if (useJndiBasedConnectionCheckBox == null) {
        	useJndiBasedConnectionCheckBox = new JCheckBox();
        	useJndiBasedConnectionCheckBox.setToolTipText("Use a JNDI-based Databse Connection?");
        	useJndiBasedConnectionCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	useJndiBasedConnectionCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("USE_JNDI_BASED_CONNECTION")));
        	useJndiBasedConnectionCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	useJndiBasedConnectionCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					toggleDbConnectionFields();
					validateInput();
				}
        	});

        	useJndiBasedConnectionCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return useJndiBasedConnectionCheckBox;
    }  
    
    /**
     * This method initializes the Database JNDI URL Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getDbJndiUrlField() {
        if (dbJndiUrlField == null) {
        	dbJndiUrlField = new JTextField();
        	dbJndiUrlField.setText(DeployPropertiesManager.getDeployPropertyValue("DB_JNDI_URL"));
        	dbJndiUrlField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	dbJndiUrlField.addFocusListener(new FocusChangeHandler());
        }
        return dbJndiUrlField;
    }
    
    /**
     * This method initializes the Database Connection URL Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getDbConnectionUrlField() {
        if (dbConnectionUrlField == null) {
        	dbConnectionUrlField = new JTextField();
        	dbConnectionUrlField.setText(DeployPropertiesManager.getDeployPropertyValue("DB_CONNECTION_URL"));
        	dbConnectionUrlField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	dbConnectionUrlField.addFocusListener(new FocusChangeHandler());
        }
        return dbConnectionUrlField;
    }
    
    /**
     * This method initializes the Database Username Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getDbUsernameField() {
        if (dbUsernameField == null) {
        	dbUsernameField = new JTextField();
        	dbUsernameField.setText(DeployPropertiesManager.getDeployPropertyValue("DB_USERNAME"));
        	dbUsernameField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	dbUsernameField.addFocusListener(new FocusChangeHandler());
        }
        return dbUsernameField;
    }
    
    /**
     * This method initializes the Database Password Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getDbPasswordField() {
        if (dbPasswordField == null) {
        	dbPasswordField = new JTextField();
        	dbPasswordField.setText(DeployPropertiesManager.getDeployPropertyValue("DB_PASSWORD"));
        	dbPasswordField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	dbPasswordField.addFocusListener(new FocusChangeHandler());
        }
        return dbPasswordField;
    }
    
    /**
     * This method initializes the Database Driver Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getDbDriverField() {
        if (dbDriverField == null) {
        	dbDriverField = new JTextField();
        	dbDriverField.setText(DeployPropertiesManager.getDeployPropertyValue("DB_DRIVER"));
        	dbDriverField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	dbDriverField.addFocusListener(new FocusChangeHandler());
        }
        return dbDriverField;
    }
    
    /**
     * This method initializes the Database Driver Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getDbDialectField() {
        if (dbDialectField == null) {
        	dbDialectField = new JTextField();
        	dbDialectField.setText(DeployPropertiesManager.getDeployPropertyValue("DB_DIALECT"));
        	dbDialectField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	dbDialectField.addFocusListener(new FocusChangeHandler());
        }
        return dbDialectField;
    }
    
    /**
     * This method initializes the Enable Writable API Extension CheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getEnableWritableApiExtensionCheckBox() {
        if (enableWritableApiExtensionCheckBox == null) {
        	enableWritableApiExtensionCheckBox = new JCheckBox();
        	enableWritableApiExtensionCheckBox.setToolTipText("Enable Writable API Extension?");
        	enableWritableApiExtensionCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	enableWritableApiExtensionCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("ENABLE_WRITABLE_API_EXTENSION")));
        	enableWritableApiExtensionCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	enableWritableApiExtensionCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					toggleWritableApiFields();
					validateInput();
				}
        	});

        	enableWritableApiExtensionCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return enableWritableApiExtensionCheckBox;
    }

    /**
     * This method initializes the Writable API Database Type Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getDatabaseTypeField() {
        if (databaseTypeField == null) {
        	databaseTypeField = new JTextField();
        	databaseTypeField.setText(DeployPropertiesManager.getDeployPropertyValue("DATABASE_TYPE"));
        	databaseTypeField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	databaseTypeField.addFocusListener(new FocusChangeHandler());
        }
        return databaseTypeField;
    }
    
    /**
     * This method initializes the Writable API Identity Generator Tag Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getIdentityGeneratorTagField() {
        if (identityGeneratorTagField == null) {
        	identityGeneratorTagField = new JTextField();
        	identityGeneratorTagField.setText(DeployPropertiesManager.getDeployPropertyValue("IDENTITY_GENERATOR_TAG"));
        	identityGeneratorTagField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	identityGeneratorTagField.addFocusListener(new FocusChangeHandler());
        }
        return identityGeneratorTagField;
    }

    /**
     * This method initializes the CLM Database Connection URL Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getCaDsrConnectionUrlField() {
        if (caDsrConnectionUrlField == null) {
        	caDsrConnectionUrlField = new JTextField();
        	caDsrConnectionUrlField.setText(DeployPropertiesManager.getDeployPropertyValue("CADSR_CONNECTION_URL"));
        	caDsrConnectionUrlField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	caDsrConnectionUrlField.addFocusListener(new FocusChangeHandler());
        }
        return caDsrConnectionUrlField;
    }
    

    
    /**
     * This method initializes the Enable Common Logging Module CheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getEnableCommonLoggingModuleCheckBox() {
        if (enableCommonLoggingModuleCheckBox == null) {
        	enableCommonLoggingModuleCheckBox = new JCheckBox();
        	enableCommonLoggingModuleCheckBox.setToolTipText("Enable Logging?");
        	enableCommonLoggingModuleCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	enableCommonLoggingModuleCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("ENABLE_WRITABLE_API_EXTENSION")));
        	enableCommonLoggingModuleCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	enableCommonLoggingModuleCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					toggleWritableApiFields();
				}
        	});

        	enableCommonLoggingModuleCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return enableCommonLoggingModuleCheckBox;
    }
    
    /**
     * This method initializes the CLM Project Name Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getClmProjectName() {
        if (clmProjectName == null) {
        	clmProjectName = new JTextField();
        	clmProjectName.setText(DeployPropertiesManager.getDeployPropertyValue("PROJECT_NAME"));
        	clmProjectName.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	clmProjectName.addFocusListener(new FocusChangeHandler());
        }
        return clmProjectName;
    }

    /**
     * This method initializes the CLM Database Connection URL Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getClmDbConnectionUrlField() {
        if (clmDbConnectionUrlField == null) {
        	clmDbConnectionUrlField = new JTextField();
        	clmDbConnectionUrlField.setText(DeployPropertiesManager.getDeployPropertyValue("DB_CONNECTION_URL"));
        	clmDbConnectionUrlField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	clmDbConnectionUrlField.addFocusListener(new FocusChangeHandler());
        }
        return clmDbConnectionUrlField;
    }
    
    /**
     * This method initializes the CLM Database Username Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getClmDbUsernameField() {
        if (clmDbUsernameField == null) {
        	clmDbUsernameField = new JTextField();
        	clmDbUsernameField.setText(DeployPropertiesManager.getDeployPropertyValue("DB_USERNAME"));
        	clmDbUsernameField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	clmDbUsernameField.addFocusListener(new FocusChangeHandler());
        }
        return clmDbUsernameField;
    }
    
    /**
     * This method initializes the CLM Database Password Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getClmDbPasswordField() {
        if (clmDbPasswordField == null) {
        	clmDbPasswordField = new JTextField();
        	clmDbPasswordField.setText(DeployPropertiesManager.getDeployPropertyValue("DB_PASSWORD"));
        	clmDbPasswordField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	clmDbPasswordField.addFocusListener(new FocusChangeHandler());
        }
        return clmDbPasswordField;
    }
    
    /**
     * This method initializes the Database Driver Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getClmDbDriverField() {
        if (clmDbDriverField == null) {
        	clmDbDriverField = new JTextField();
        	clmDbDriverField.setText(DeployPropertiesManager.getDeployPropertyValue("DB_DRIVER"));
        	clmDbDriverField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	clmDbDriverField.addFocusListener(new FocusChangeHandler());
        }
        return clmDbDriverField;
    }
    
    /**
     * This method initializes the Enable Common Logging Module CheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getEnableSecurityCheckBox() {
        if (enableSecurityCheckBox == null) {
        	enableSecurityCheckBox = new JCheckBox();
        	enableSecurityCheckBox.setToolTipText("Enable Security Extension?");
        	enableSecurityCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	enableSecurityCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("ENABLE_SECURITY")));
        	enableSecurityCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	enableSecurityCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					toggleSecurityFields();
					validateInput();
				}
        	});

        	enableSecurityCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return enableSecurityCheckBox;
    }
    
    /**
     * This method initializes the Enable Instance Level Security CheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getEnableInstanceLevelSecurityCheckBox() {
        if (enableInstanceLevelSecurityCheckBox == null) {
        	enableInstanceLevelSecurityCheckBox = new JCheckBox();
        	enableInstanceLevelSecurityCheckBox.setToolTipText("Enable Instance Level Security?");
        	enableInstanceLevelSecurityCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	enableInstanceLevelSecurityCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("ENABLE_INSTANCE_LEVEL_SECURITY")));
        	enableInstanceLevelSecurityCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	enableInstanceLevelSecurityCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					toggleSecurityFields();
					validateInput();
				}
        	});

        	enableInstanceLevelSecurityCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return enableInstanceLevelSecurityCheckBox;
    }
    
    /**
     * This method initializes the Enable Attribute Level Security CheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getEnableAttributeLevelSecurityCheckBox() {
        if (enableAttributeLevelSecurityCheckBox == null) {
        	enableAttributeLevelSecurityCheckBox = new JCheckBox();
        	enableAttributeLevelSecurityCheckBox.setToolTipText("Enable Attribute Level Security?");
        	enableAttributeLevelSecurityCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	enableAttributeLevelSecurityCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("ENABLE_ATTRIBUTE_LEVEL_SECURITY")));
        	enableAttributeLevelSecurityCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	enableAttributeLevelSecurityCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					toggleSecurityFields();
					validateInput();
				}
        	});

        	enableAttributeLevelSecurityCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return enableAttributeLevelSecurityCheckBox;
    }
    
    /**
     * This method initializes the CSM Project Name Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getCsmProjectNameField() {
        if (csmProjectNameField == null) {
        	csmProjectNameField = new JTextField();
        	csmProjectNameField.setText(DeployPropertiesManager.getDeployPropertyValue("PROJECT_NAME"));
        	csmProjectNameField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	csmProjectNameField.addFocusListener(new FocusChangeHandler());
        }
        return csmProjectNameField;
    } 
    
    /**
     * This method initializes the Cache Protection Elements CheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCacheProtectionElementsCheckBox() {
        if (cacheProtectionElementsCheckBox == null) {
        	cacheProtectionElementsCheckBox = new JCheckBox();
        	cacheProtectionElementsCheckBox.setToolTipText("Cache Protection Elements?");
        	cacheProtectionElementsCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	cacheProtectionElementsCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("CACHE_PROTECTION_ELEMENTS")));
        	cacheProtectionElementsCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	cacheProtectionElementsCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					toggleSecurityFields();
					validateInput();
				}
        	});

        	cacheProtectionElementsCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return cacheProtectionElementsCheckBox;
    }

    /**
     * This method initializes the CSM Use JNDI Based Connection Check Box
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCsmUseJndiBasedConnectionCheckBox() {
        if (csmUseJndiBasedConnectionCheckBox == null) {
        	csmUseJndiBasedConnectionCheckBox = new JCheckBox();
        	csmUseJndiBasedConnectionCheckBox.setToolTipText("Use a JNDI-based CSM Databse Connection?");
        	csmUseJndiBasedConnectionCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	csmUseJndiBasedConnectionCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("USE_JNDI_BASED_CONNECTION")));
        	csmUseJndiBasedConnectionCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	csmUseJndiBasedConnectionCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					toggleCsmDbConnectionFields();
					validateInput();
				}
        	});

        	csmUseJndiBasedConnectionCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return csmUseJndiBasedConnectionCheckBox;
    }  
    
    /**
     * This method initializes the CSM Database JNDI URL Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getCsmDbJndiUrlField() {
        if (csmDbJndiUrlField == null) {
        	csmDbJndiUrlField = new JTextField();
        	csmDbJndiUrlField.setText(DeployPropertiesManager.getDeployPropertyValue("DB_JNDI_URL"));
        	csmDbJndiUrlField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	csmDbJndiUrlField.addFocusListener(new FocusChangeHandler());
        }
        return csmDbJndiUrlField;
    }
    
    /**
     * This method initializes the CSM Database Connection URL Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getCsmDbConnectionUrlField() {
        if (csmDbConnectionUrlField == null) {
        	csmDbConnectionUrlField = new JTextField();
        	csmDbConnectionUrlField.setText(DeployPropertiesManager.getDeployPropertyValue("DB_CONNECTION_URL"));
        	csmDbConnectionUrlField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	csmDbConnectionUrlField.addFocusListener(new FocusChangeHandler());
        }
        return csmDbConnectionUrlField;
    }
    
    /**
     * This method initializes the CSM Database Username Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getCsmDbUsernameField() {
        if (csmDbUsernameField == null) {
        	csmDbUsernameField = new JTextField();
        	csmDbUsernameField.setText(DeployPropertiesManager.getDeployPropertyValue("DB_USERNAME"));
        	csmDbUsernameField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	csmDbUsernameField.addFocusListener(new FocusChangeHandler());
        }
        return csmDbUsernameField;
    }
    
    /**
     * This method initializes the CSM Database Password Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getCsmDbPasswordField() {
        if (csmDbPasswordField == null) {
        	csmDbPasswordField = new JTextField();
        	csmDbPasswordField.setText(DeployPropertiesManager.getDeployPropertyValue("DB_PASSWORD"));
        	csmDbPasswordField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	csmDbPasswordField.addFocusListener(new FocusChangeHandler());
        }
        return csmDbPasswordField;
    }
    
    /**
     * This method initializes the CSM Database Driver Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getCsmDbDriverField() {
        if (csmDbDriverField == null) {
        	csmDbDriverField = new JTextField();
        	csmDbDriverField.setText(DeployPropertiesManager.getDeployPropertyValue("DB_DRIVER"));
        	csmDbDriverField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	csmDbDriverField.addFocusListener(new FocusChangeHandler());
        }
        return csmDbDriverField;
    }
    
    /**
     * This method initializes the Database Dialect Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getCsmDbDialectField() {
        if (csmDbDialectField == null) {
        	csmDbDialectField = new JTextField();
        	csmDbDialectField.setText(DeployPropertiesManager.getDeployPropertyValue("DB_DIALECT"));
        	csmDbDialectField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }
            });
        	csmDbDialectField.addFocusListener(new FocusChangeHandler());
        }
        return csmDbDialectField;
    }
    	
    /**
     * This method initializes the caGrid Authentication Service URL Field
     * 
     * @return javax.swing.JTextField
     */    
    private JTextField getCaGridAuthSvcUrlField() {
    	if (caGridAuthSvcUrlField == null) {
    		caGridAuthSvcUrlField = new JTextField();
    		caGridAuthSvcUrlField.setText(DeployPropertiesManager.getDeployPropertyValue("CAGRID_AUTHENTICATION_SERVICE_URL"));
    		caGridAuthSvcUrlField.getDocument().addDocumentListener(new DocumentListener() {
    			public void changedUpdate(DocumentEvent e) {
    				validateInput();
    			}

    			public void removeUpdate(DocumentEvent e) {
    				validateInput();
    			}

    			public void insertUpdate(DocumentEvent e) {
    				validateInput();
    			}
    		});
    		caGridAuthSvcUrlField.addFocusListener(new FocusChangeHandler());
    	}
    	return caGridAuthSvcUrlField;
    }
    
    /**
     * This method initializes the caGrid Dorian Service URL Field
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getCaGridDorianSvcUrlField() {
    	if (caGridDorianSvcUrlField == null) {
    		caGridDorianSvcUrlField = new JTextField();
    		caGridDorianSvcUrlField.setText(DeployPropertiesManager.getDeployPropertyValue("CAGRID_DORIAN_SERVICE_URL"));
    		caGridDorianSvcUrlField.getDocument().addDocumentListener(new DocumentListener() {
    			public void changedUpdate(DocumentEvent e) {
    				validateInput();
    			}

    			public void removeUpdate(DocumentEvent e) {
    				validateInput();
    			}

    			public void insertUpdate(DocumentEvent e) {
    				validateInput();
    			}
    		});
    		caGridDorianSvcUrlField.addFocusListener(new FocusChangeHandler());
    	}
    	return caGridDorianSvcUrlField;
    }
    
    /**
     * This method initializes the SDK Grid Login Service Name Field
     * 
     * @return javax.swing.JTextField
     */    
    private JTextField getSdkGridLoginSvcNameField() {
    	if (sdkGridLoginSvcNameField == null) {
    		sdkGridLoginSvcNameField = new JTextField();
    		sdkGridLoginSvcNameField.setText(DeployPropertiesManager.getDeployPropertyValue("SDK_GRID_LOGIN_SERVICE_NAME"));
    		sdkGridLoginSvcNameField.getDocument().addDocumentListener(new DocumentListener() {
    			public void changedUpdate(DocumentEvent e) {
    				validateInput();
    			}

    			public void removeUpdate(DocumentEvent e) {
    				validateInput();
    			}

    			public void insertUpdate(DocumentEvent e) {
    				validateInput();
    			}
    		});
    		sdkGridLoginSvcNameField.addFocusListener(new FocusChangeHandler());
    	}
    	return sdkGridLoginSvcNameField;
    }
    
    /**
     * This method initializes the SDK Grid Login Service URL Field
     * 
     * @return javax.swing.JTextField
     */     
    private JTextField getSdkGridLoginSvcUrlField() {
    	if (sdkGridLoginSvcUrlField == null) {
    		sdkGridLoginSvcUrlField = new JTextField();
    		
    		String sdkGridLoginServiceURL = DeployPropertiesManager.getDeployPropertyValue("SDK_GRID_LOGIN_SERVICE_URL");
    		String sdkGridLoginServiceName = DeployPropertiesManager.getDeployPropertyValue("SDK_GRID_LOGIN_SERVICE_NAME");
    		sdkGridLoginServiceURL = sdkGridLoginServiceURL.substring(0, sdkGridLoginServiceURL.indexOf('$'))+sdkGridLoginServiceName;
    		sdkGridLoginSvcUrlField.setText(sdkGridLoginServiceURL);
    		sdkGridLoginSvcUrlField.getDocument().addDocumentListener(new DocumentListener() {
    			public void changedUpdate(DocumentEvent e) {
    				validateInput();
    			}

    			public void removeUpdate(DocumentEvent e) {
    				validateInput();
    			}

    			public void insertUpdate(DocumentEvent e) {
    				validateInput();
    			}
    		});
    		sdkGridLoginSvcUrlField.addFocusListener(new FocusChangeHandler());
    	}
    	return sdkGridLoginSvcUrlField;
    }
    
    /**
     * This method initializes the Enable caGrid Login Module Check Box
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getEnableCaGridLoginModuleCheckBox() {
        if (enableCaGridLoginModuleCheckBox == null) {
        	enableCaGridLoginModuleCheckBox = new JCheckBox();
        	enableCaGridLoginModuleCheckBox.setToolTipText("Enable Grid Login Module?");
        	enableCaGridLoginModuleCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	enableCaGridLoginModuleCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("ENABLE_GRID_LOGIN_MODULE")));
        	enableCaGridLoginModuleCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	enableCaGridLoginModuleCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					toggleCaGridLoginFields();
					validateInput();
				}
        	});

        	enableCaGridLoginModuleCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return enableCaGridLoginModuleCheckBox;
    }
    
    /**
     * This method initializes the Enable CSM Login Module Check Box
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getEnableCsmLoginModuleCheckBox() {
        if (enableCsmLoginModuleCheckBox == null) {
        	enableCsmLoginModuleCheckBox = new JCheckBox();
        	enableCsmLoginModuleCheckBox.setToolTipText("Enable CSM Login Module?");
        	enableCsmLoginModuleCheckBox.setHorizontalAlignment(SwingConstants.LEADING);
        	enableCsmLoginModuleCheckBox.setSelected(Boolean.parseBoolean(DeployPropertiesManager.getDeployPropertyValue("ENABLE_CSM_LOGIN_MODULE")));
        	enableCsmLoginModuleCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
			
        	enableCsmLoginModuleCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					toggleCsmLoginFields();
					validateInput();
				}
        	});

        	enableCsmLoginModuleCheckBox.addFocusListener(new FocusChangeHandler());
        }
        return enableCsmLoginModuleCheckBox;
    }
    
    /**
     * This method initializes the caGrid Login Module Name Field
     * 
     * @return javax.swing.JTextField
     */         
    private JTextField getCaGridLoginModuleNameField() {
    	if (caGridLoginModuleNameField == null) {
    		caGridLoginModuleNameField = new JTextField();
    		caGridLoginModuleNameField.setText(DeployPropertiesManager.getDeployPropertyValue("CAGRID_LOGIN_MODULE_NAME"));
    		caGridLoginModuleNameField.getDocument().addDocumentListener(new DocumentListener() {
    			public void changedUpdate(DocumentEvent e) {
    				validateInput();
    			}

    			public void removeUpdate(DocumentEvent e) {
    				validateInput();
    			}

    			public void insertUpdate(DocumentEvent e) {
    				validateInput();
    			}
    		});
    		caGridLoginModuleNameField.addFocusListener(new FocusChangeHandler());
    	}
    	return caGridLoginModuleNameField;
    }
   
    /**
     * This method initializes the Cache Path Field
     * 
     * @return javax.swing.JTextField
     */         
    private JTextField getCachePathField() {
    	if (cachePathField == null) {
    		cachePathField = new JTextField();
    		cachePathField.setText(DeployPropertiesManager.getDeployPropertyValue("CACHE_PATH"));
    		
    		cachePathField.getDocument().addDocumentListener(new DocumentListener() {
    			public void changedUpdate(DocumentEvent e) {
    				validateInput();
    			}

    			public void removeUpdate(DocumentEvent e) {
    				validateInput();
    			}

    			public void insertUpdate(DocumentEvent e) {
    				validateInput();
    			}
    		});
    		cachePathField.addFocusListener(new FocusChangeHandler());
    	}
    	return cachePathField;
    }
    
    /**
     * This method initializes the Server Type Combo Box
     * 
     * @return javax.swing.JTextField
     */         
    private JComboBox getServerTypeComboBox() {
    	if (serverTypeComboBox == null) {
    		serverTypeComboBox = new JComboBox();
    		serverTypeComboBox.addItem("jboss");
    		serverTypeComboBox.addItem("other");
    		
    		String serverType = DeployPropertiesManager.getDeployPropertyValue("SERVER_TYPE");

    		if (serverType.equalsIgnoreCase("jboss")){
    				serverTypeComboBox.setSelectedItem("jboss");
    		} else{
    			serverTypeComboBox.setSelectedItem("other");
    		}
    		
//    		serverTypeField.getDocument().addDocumentListener(new DocumentListener() {
//    			public void changedUpdate(DocumentEvent e) {
//    				validateInput();
//    			}
//
//    			public void removeUpdate(DocumentEvent e) {
//    				validateInput();
//    			}
//
//    			public void insertUpdate(DocumentEvent e) {
//    				validateInput();
//    			}
//    		});
    		serverTypeComboBox.addFocusListener(new FocusChangeHandler());
    	}
    	return serverTypeComboBox;
    }
    
    /**
     * This method initializes the Server URL Field
     * 
     * @return javax.swing.JTextField
     */         
    private JTextField getServerUrlField() {
    	if (serverUrlField == null) {
    		serverUrlField = new JTextField();
    		
    		String projectName = DeployPropertiesManager.getDeployPropertyValue("PROJECT_NAME");
    		String serverUrl = DeployPropertiesManager.getDeployPropertyValue("SERVER_URL");
    		serverUrl = serverUrl.substring(0, serverUrl.indexOf('$'))+projectName;
    		serverUrlField.setText(serverUrl);
    		
    		serverUrlField.getDocument().addDocumentListener(new DocumentListener() {
    			public void changedUpdate(DocumentEvent e) {
    				validateInput();
    			}

    			public void removeUpdate(DocumentEvent e) {
    				validateInput();
    			}

    			public void insertUpdate(DocumentEvent e) {
    				validateInput();
    			}
    		});
    		serverUrlField.addFocusListener(new FocusChangeHandler());
    	}
    	return serverUrlField;
    }
    
    //TODO
    
    /**
     * This method initializes closeButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getCloseButton() {
        if (closeButton == null) {
            closeButton = new JButton();
            closeButton.setIcon(PortalLookAndFeel.getCloseIcon());
            closeButton.setText("Cancel");
            closeButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    dispose();
                }
            });
        }
        return closeButton;
    }

	/**
	 * This method initializes mainTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getMainTabbedPane() {
		if (mainTabbedPane == null) {
			mainTabbedPane = new JTabbedPane();
			mainTabbedPane.addTab("Project", null, new IconFeedbackPanel(this.validationModel, getProjectSettingsPanel()), null);
			mainTabbedPane.addTab("Model", null, new IconFeedbackPanel(this.validationModel, getModelSettingsPanel()), null);
			mainTabbedPane.addTab("Code Gen", null, new IconFeedbackPanel(this.validationModel, getCodeGenSettingsPanel()), null);
			mainTabbedPane.addTab("DB", null, new IconFeedbackPanel(this.validationModel, getDbConnectionSettingsPanel()), null);
			
			mainTabbedPane.addTab("Writable API", null, new IconFeedbackPanel(this.validationModel, getWritableApiSettingsPanel()), null);
			mainTabbedPane.addTab("Logging", null, new IconFeedbackPanel(this.validationModel, getClmSettingsPanel()), null);
			
			mainTabbedPane.addTab("Security", null, new IconFeedbackPanel(this.validationModel, getSecuritySettingsPanel()), null);
			mainTabbedPane.addTab("CSM DB", null, new IconFeedbackPanel(this.validationModel, getCsmDbConnectionSettingsPanel()), null);
			mainTabbedPane.addTab("caGrid Auth", null, new IconFeedbackPanel(this.validationModel, getCaGridAuthSettingsPanel()), null);
			
			mainTabbedPane.addTab("App Server", null, new IconFeedbackPanel(this.validationModel, getAppServerSettingsPanel()), null);

			mainTabbedPane.addTab("Advanced", null, new IconFeedbackPanel(this.validationModel, getAdvancedSettingsPanel()), null);
		}
		return mainTabbedPane;
	}
	
	/**
	 * This method initializes securitySettingsSubPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSecuritySettingsSubPanel() {
		if (securitySettingsSubPanel == null) {
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints10.gridy = 1;
			gridBagConstraints10.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints10.gridx = 0;

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.weighty = 1.0D;
			gridBagConstraints11.weightx = 1.0D;  
			gridBagConstraints11.gridwidth = 2;

			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints20.gridy = 2;
			gridBagConstraints20.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints20.gridx = 0;            

			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints21.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints21.gridy = 2;
			gridBagConstraints21.gridx = 1;
			gridBagConstraints21.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints21.gridwidth = 2;
			gridBagConstraints21.weighty = 1.0D;
			gridBagConstraints21.weightx = 1.0D;  

			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints30.gridy = 3;
			gridBagConstraints30.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints30.gridx = 0;

			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints31.gridy = 3;
			gridBagConstraints31.weightx = 1.0;
			gridBagConstraints31.gridwidth = 2;
			gridBagConstraints31.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints31.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints31.weighty = 1.0D;
			gridBagConstraints31.gridx = 1;

			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
			gridBagConstraints40.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints40.gridy = 4;
			gridBagConstraints40.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints40.gridx = 0;

			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints41.gridy = 4;
			gridBagConstraints41.weightx = 1.0;
			gridBagConstraints41.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints41.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints41.gridwidth = 2;
			gridBagConstraints41.weighty = 1.0D;
			gridBagConstraints41.gridx = 1;

//			GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
//			gridBagConstraints50.anchor = java.awt.GridBagConstraints.WEST;
//			gridBagConstraints50.gridy = 5;
//			gridBagConstraints50.insets = new java.awt.Insets(2, 2, 2, 2);
//			gridBagConstraints50.gridx = 0;
//
//			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
//			gridBagConstraints51.fill = java.awt.GridBagConstraints.HORIZONTAL;
//			gridBagConstraints51.gridy = 5;
//			gridBagConstraints51.weightx = 1.0;
//			gridBagConstraints51.gridwidth = 2;
//			gridBagConstraints51.anchor = java.awt.GridBagConstraints.WEST;
//			gridBagConstraints51.insets = new java.awt.Insets(2, 2, 2, 2);
//			gridBagConstraints51.weighty = 1.0D;
//			gridBagConstraints51.gridx = 1;
		    
//		    enableSecurityLabel = new JLabel();
//		    enableSecurityLabel.setText("Enable Security Extension?");
		    
		    enableInstanceLevelSecurityLabel = new JLabel();
		    enableInstanceLevelSecurityLabel.setText("Enable Instance Level Security?");
		    
		    enableAttributeLevelSecurityLabel = new JLabel();
		    enableAttributeLevelSecurityLabel.setText("Enable Attribute Level Security?");

		    csmProjectNameLabel = new JLabel();
		    csmProjectNameLabel.setText("Enter CSM Project Name:");

		    cacheProtectionElementsLabel = new JLabel();
		    cacheProtectionElementsLabel.setText("Cache Protection Elements?");

		    securitySettingsSubPanel = new JPanel();
		    securitySettingsSubPanel.setLayout(new GridBagLayout());
		    securitySettingsSubPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Security Options",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, PortalLookAndFeel.getPanelLabelColor()));

//			securitySettingsSubPanel.add(enableSecurityLabel, gridBagConstraints10);
//			securitySettingsSubPanel.add(getEnableSecurityCheckBox(), gridBagConstraints11);
		    securitySettingsSubPanel.add(enableInstanceLevelSecurityLabel, gridBagConstraints10);
		    securitySettingsSubPanel.add(getEnableInstanceLevelSecurityCheckBox(), gridBagConstraints11);
		    securitySettingsSubPanel.add(enableAttributeLevelSecurityLabel, gridBagConstraints20);
		    securitySettingsSubPanel.add(getEnableAttributeLevelSecurityCheckBox(), gridBagConstraints21);
		    securitySettingsSubPanel.add(csmProjectNameLabel, gridBagConstraints30);
		    securitySettingsSubPanel.add(getCsmProjectNameField(), gridBagConstraints31);
		    securitySettingsSubPanel.add(cacheProtectionElementsLabel, gridBagConstraints40);
		    securitySettingsSubPanel.add(getCacheProtectionElementsCheckBox(), gridBagConstraints41);

		    securitySettingsSubPanel.validate();
		}
		return securitySettingsSubPanel;
	}
	
	/**
	 * This method initializes securitySettingsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSecuritySettingsPanel() {
		if (securitySettingsPanel == null) {
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints10.gridy = 1;
			gridBagConstraints10.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints10.gridx = 0;

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.weighty = 1.0D;
			gridBagConstraints11.weightx = 1.0D;  
			gridBagConstraints11.gridwidth = 2;

			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints20.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints20.gridy = 2;
			gridBagConstraints20.gridx = 0;
			gridBagConstraints20.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints20.gridwidth = 3;
			gridBagConstraints20.weighty = 1.0D;
			gridBagConstraints20.weightx = 1.0D;  
	    
		    enableSecurityLabel = new JLabel();
		    enableSecurityLabel.setText("Enable Security Extension?");

			securitySettingsPanel = new JPanel();
			securitySettingsPanel.setLayout(new GridBagLayout());
			securitySettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Define Security Properties",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, PortalLookAndFeel.getPanelLabelColor()));

			securitySettingsPanel.add(enableSecurityLabel, gridBagConstraints10);
			securitySettingsPanel.add(getEnableSecurityCheckBox(), gridBagConstraints11);
			securitySettingsPanel.add(getSecuritySettingsSubPanel(), gridBagConstraints20);

			securitySettingsPanel.validate();
		}
		return securitySettingsPanel;
	}
	
	/**
	 * This method initializes writableApiSettingsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getWritableApiSettingsPanel() {
		if (writableApiSettingsPanel == null) {

			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints10.gridy = 1;
			gridBagConstraints10.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints10.gridx = 0;

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.weighty = 1.0D;
			gridBagConstraints11.weightx = 1.0D;  
			gridBagConstraints11.gridwidth = 2;

			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints20.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints20.gridy = 2;
			gridBagConstraints20.gridx = 0;
			gridBagConstraints20.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints20.gridwidth = 3;
			gridBagConstraints20.weighty = 1.0D;
			gridBagConstraints20.weightx = 1.0D;  

		    enableWritableApiExtensionLabel = new JLabel();
		    enableWritableApiExtensionLabel.setText("Enable Writable API Extension?");
		    
			writableApiSettingsPanel = new JPanel();
			writableApiSettingsPanel.setLayout(new GridBagLayout());
			writableApiSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Define Writable API Module Properties",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, PortalLookAndFeel.getPanelLabelColor()));

			writableApiSettingsPanel.add(enableWritableApiExtensionLabel, gridBagConstraints10);
			writableApiSettingsPanel.add(getEnableWritableApiExtensionCheckBox(), gridBagConstraints11);
			writableApiSettingsPanel.add(getWritableApiSettingsSubPanel(), gridBagConstraints20);

			writableApiSettingsPanel.validate();
		}
		return writableApiSettingsPanel;
	}
	
	/**
	 * This method initializes writableApiSettingsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getWritableApiSettingsSubPanel() {
		if (writableApiSettingsSubPanel == null) {

			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints10.gridy = 1;
			gridBagConstraints10.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints10.gridx = 0;

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.weighty = 1.0D;
			gridBagConstraints11.weightx = 1.0D;  
			gridBagConstraints11.gridwidth = 2;

			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints20.gridy = 2;
			gridBagConstraints20.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints20.gridx = 0;            

			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints21.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints21.gridy = 2;
			gridBagConstraints21.gridx = 1;
			gridBagConstraints21.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints21.gridwidth = 2;
			gridBagConstraints21.weighty = 1.0D;
			gridBagConstraints21.weightx = 1.0D;  

			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints30.gridy = 3;
			gridBagConstraints30.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints30.gridx = 0;

			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints31.gridy = 3;
			gridBagConstraints31.weightx = 1.0;
			gridBagConstraints31.gridwidth = 2;
			gridBagConstraints31.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints31.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints31.weighty = 1.0D;
			gridBagConstraints31.gridx = 1;

		    databaseTypeLabel = new JLabel();
		    databaseTypeLabel.setText("Enter Database Type:");

		    identityGeneratorTagLabel = new JLabel();
		    identityGeneratorTagLabel.setText("Enter Hibernate Identity Generator Tag:");

		    caDsrConnectionUrlLabel = new JLabel();
		    caDsrConnectionUrlLabel.setText("Enter caDSR Connection URL:");
		    
			writableApiSettingsSubPanel = new JPanel();
			writableApiSettingsSubPanel.setLayout(new GridBagLayout());
			writableApiSettingsSubPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Writable API Options",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, PortalLookAndFeel.getPanelLabelColor()));

			writableApiSettingsSubPanel.add(databaseTypeLabel, gridBagConstraints10);
			writableApiSettingsSubPanel.add(getDatabaseTypeField(), gridBagConstraints11);
			writableApiSettingsSubPanel.add(identityGeneratorTagLabel, gridBagConstraints20);
			writableApiSettingsSubPanel.add(getIdentityGeneratorTagField(), gridBagConstraints21);
			writableApiSettingsSubPanel.add(caDsrConnectionUrlLabel, gridBagConstraints30);
			writableApiSettingsSubPanel.add(getCaDsrConnectionUrlField(), gridBagConstraints31);

			writableApiSettingsSubPanel.validate();
		}
		return writableApiSettingsSubPanel;
	}
	
	/**
	 * This method initializes writableApiSettingsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getClmSettingsPanel() {
		if (clmSettingsPanel == null) {

			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints10.gridy = 1;
			gridBagConstraints10.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints10.gridx = 0;

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints11.gridy = 1;
//			gridBagConstraints11.weighty = 1.0D;  // so that the CLM sub panel has priority
			gridBagConstraints11.weightx = 1.0D;  
			gridBagConstraints11.gridwidth = 2;
			
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints20.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints20.gridy = 2;
			gridBagConstraints20.gridx = 0;
			gridBagConstraints20.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints20.gridwidth = 3;
//			gridBagConstraints20.weighty = 1.0D;
			gridBagConstraints20.weightx = 1.0D;  
			
			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints30.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints30.gridy = 2;
			gridBagConstraints30.gridx = 0;
			gridBagConstraints30.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints30.gridwidth = 3;
			gridBagConstraints30.weighty = 1.0D;
			gridBagConstraints30.weightx = 1.0D;

		    enableCommonLoggingModuleLabel = new JLabel();
		    enableCommonLoggingModuleLabel.setText("Enable Logging?");
		    
		    clmSettingsPanel = new JPanel();
			clmSettingsPanel.setLayout(new GridBagLayout());
			clmSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Define Logging Properties",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, PortalLookAndFeel.getPanelLabelColor()));

			clmSettingsPanel.add(enableCommonLoggingModuleLabel, gridBagConstraints10);
			clmSettingsPanel.add(getEnableCommonLoggingModuleCheckBox(), gridBagConstraints11);
			clmSettingsPanel.add(getLoggingSettingsSubPanel(), gridBagConstraints20);

			clmSettingsPanel.validate();
		}
		return clmSettingsPanel;
	}
	
	
	/**
	 * This method initializes clmSettingsSubPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getLoggingSettingsSubPanel() {
		if (clmSettingsSubPanel == null) {

			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints10.gridy = 5;
			gridBagConstraints10.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints10.gridx = 0;

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.gridy = 5;
			gridBagConstraints11.weightx = 1.0;
			gridBagConstraints11.gridwidth = 2;
			gridBagConstraints11.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints11.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints11.weighty = 1.0D;
			gridBagConstraints11.gridx = 1;

			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints20.gridy = 6;
			gridBagConstraints20.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints20.gridx = 0;

			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints21.gridy = 6;
			gridBagConstraints21.weightx = 1.0;
			gridBagConstraints21.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints21.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints21.gridwidth = 2;
			gridBagConstraints21.weighty = 1.0D;
			gridBagConstraints21.gridx = 1;

			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints30.gridy = 7;
			gridBagConstraints30.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints30.gridx = 0;

			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints31.gridy = 7;
			gridBagConstraints31.weightx = 1.0;
			gridBagConstraints31.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints31.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints31.gridwidth = 2;
			gridBagConstraints31.weighty = 1.0D;
			gridBagConstraints31.gridx = 1;

			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
			gridBagConstraints40.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints40.gridy = 8;
			gridBagConstraints40.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints40.gridx = 0;

			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints41.gridy = 8;
			gridBagConstraints41.weightx = 1.0;
			gridBagConstraints41.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints41.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints41.gridwidth = 2;
			gridBagConstraints41.weighty = 1.0D;
			gridBagConstraints41.gridx = 1;

			GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
			gridBagConstraints50.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints50.gridy = 9;
			gridBagConstraints50.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints50.gridx = 0;

			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints51.gridy = 9;
			gridBagConstraints51.weightx = 1.0;
			gridBagConstraints51.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints51.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints51.gridwidth = 2;
			gridBagConstraints51.weighty = 1.0D;
			gridBagConstraints51.gridx = 1;

			GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
			gridBagConstraints60.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints60.gridy = 10;
			gridBagConstraints60.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints60.gridx = 0;

			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints61.gridy = 10;
			gridBagConstraints61.weightx = 1.0;
			gridBagConstraints61.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints61.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints61.gridwidth = 2;
			gridBagConstraints61.weighty = 1.0D;
			gridBagConstraints61.gridx = 1;

		    clmProjectNameLabel = new JLabel();
		    clmProjectNameLabel.setText("Enter Logging Project Name:");

		    clmDbConnectionUrlLabel = new JLabel();
		    clmDbConnectionUrlLabel.setText("Enter Logging Database Connection URL:");

		    clmDbUsernameLabel = new JLabel();
		    clmDbUsernameLabel.setText("Enter Logging Database Username:");

		    clmDbPasswordLabel = new JLabel();
		    clmDbPasswordLabel.setText("Enter Logging Database Password:");

		    clmDbDriverLabel = new JLabel();
		    clmDbDriverLabel.setText("Enter Logging Database Driver:");
		    
		    clmSettingsSubPanel = new JPanel();
		    clmSettingsSubPanel.setLayout(new GridBagLayout());
		    clmSettingsSubPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Logging Database Properties",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, PortalLookAndFeel.getPanelLabelColor()));

		    clmSettingsSubPanel.add(clmProjectNameLabel, gridBagConstraints10);
		    clmSettingsSubPanel.add(getClmProjectName(), gridBagConstraints11);
		    clmSettingsSubPanel.add(clmDbConnectionUrlLabel, gridBagConstraints20);
		    clmSettingsSubPanel.add(getClmDbConnectionUrlField(), gridBagConstraints21);
		    clmSettingsSubPanel.add(clmDbUsernameLabel, gridBagConstraints30);
		    clmSettingsSubPanel.add(getClmDbUsernameField(), gridBagConstraints31);   
		    clmSettingsSubPanel.add(clmDbPasswordLabel, gridBagConstraints40);
		    clmSettingsSubPanel.add(getClmDbPasswordField(), gridBagConstraints41);
		    clmSettingsSubPanel.add(clmDbDriverLabel, gridBagConstraints50);
		    clmSettingsSubPanel.add(getClmDbDriverField(), gridBagConstraints51);

		    clmSettingsSubPanel.validate();
		}
		return clmSettingsSubPanel;
	}
	
    /**
     * This method initializes the Project Settings jPanel
     */
    private JPanel getProjectSettingsPanel() {
        if (projectSettingsPanel == null) {
        	
            GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
            gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints10.gridy = 1;
            gridBagConstraints10.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints10.gridx = 0;
            
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints11.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints11.gridx = 1;
            gridBagConstraints11.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints11.gridy = 1;
            gridBagConstraints11.weighty = 1.0D;
            gridBagConstraints11.weightx = 1.0;          
            
            GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints12.gridy = 1;
            gridBagConstraints12.gridx = 2;
            gridBagConstraints12.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints12.gridwidth = 1;
            
            GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
            gridBagConstraints20.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints20.gridy = 2;
            gridBagConstraints20.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints20.gridx = 0;            

            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            gridBagConstraints21.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints21.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints21.gridy = 2;
            gridBagConstraints21.gridx = 1;
            gridBagConstraints21.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints21.gridwidth = 2;
            gridBagConstraints21.weighty = 1.0D;
            gridBagConstraints21.weightx = 1.0;
            
            GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
            gridBagConstraints30.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints30.gridy = 3;
            gridBagConstraints30.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints30.gridx = 0;
            
            GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
            gridBagConstraints31.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints31.gridy = 3;
            gridBagConstraints31.weightx = 1.0;
            gridBagConstraints31.gridwidth = 2;
            gridBagConstraints31.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints31.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints31.weighty = 1.0D;
            gridBagConstraints31.gridx = 1;
     
            GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
            gridBagConstraints40.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints40.gridy = 4;
            gridBagConstraints40.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints40.gridx = 0;
            
            GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
            gridBagConstraints41.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints41.gridy = 4;
            gridBagConstraints41.weightx = 1.0;
            gridBagConstraints41.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints41.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints41.gridwidth = 2;
            gridBagConstraints41.weighty = 1.0D;
            gridBagConstraints41.gridx = 1;
                        
            sdkInstallDirLabel = new JLabel();
            sdkInstallDirLabel.setText("Select your existing SDK Home directory:");
            sdkInstallDirLabel.setName("SDK Directory");
            
            projectNameLabel = new JLabel();
            projectNameLabel.setText("Enter the Project Name:");
            
            namespacePrefixLabel = new JLabel();
            namespacePrefixLabel.setText("Enter the Project Namespace Prefix:");
            
            webserviceNameLabel = new JLabel();
            webserviceNameLabel.setText("Enter the Project Web Service Name:");
            
            projectSettingsPanel = new JPanel();
            projectSettingsPanel.setLayout(new GridBagLayout());
            projectSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Define Project Properties",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, null, PortalLookAndFeel.getPanelLabelColor()));
            
            projectSettingsPanel.add(sdkInstallDirLabel, gridBagConstraints10);
            projectSettingsPanel.add(getSdkInstallDirField(), gridBagConstraints11);
            projectSettingsPanel.add(getSdkInstallDirButton(), gridBagConstraints12);
            projectSettingsPanel.add(projectNameLabel, gridBagConstraints20);
            projectSettingsPanel.add(getProjectNameField(), gridBagConstraints21);
            projectSettingsPanel.add(namespacePrefixLabel, gridBagConstraints30);
            projectSettingsPanel.add(getNamespacePrefixField(), gridBagConstraints31);
            projectSettingsPanel.add(webserviceNameLabel, gridBagConstraints40);
            projectSettingsPanel.add(getWebServiceNameField(), gridBagConstraints41);
            
            projectSettingsPanel.validate();
        }
        return projectSettingsPanel;
    }
	
	/**
	 * This method initializes modelSettingsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getModelSettingsPanel() {
		if (modelSettingsPanel == null) {
			
            GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
            gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints10.gridy = 1;
            gridBagConstraints10.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints10.gridx = 0;
            
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints11.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints11.gridx = 1;
            gridBagConstraints11.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints11.gridy = 1;
            gridBagConstraints11.weighty = 1.0D;
            gridBagConstraints11.weightx = 1.0;          
            
            GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints12.gridy = 1;
            gridBagConstraints12.gridx = 2;
            gridBagConstraints12.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints12.gridwidth = 1;
            
            GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
            gridBagConstraints20.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints20.gridy = 2;
            gridBagConstraints20.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints20.gridx = 0;            

            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            gridBagConstraints21.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints21.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints21.gridy = 2;
            gridBagConstraints21.gridx = 1;
            gridBagConstraints21.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints21.gridwidth = 1;
            gridBagConstraints21.weightx = 0.3D;
            
            GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
            gridBagConstraints30.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints30.gridy = 3;
            gridBagConstraints30.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints30.gridx = 0;
            
            GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
            gridBagConstraints31.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints31.gridy = 3;
            gridBagConstraints31.weightx = 1.0;
            gridBagConstraints31.gridwidth = 2;
            gridBagConstraints31.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints31.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints31.weighty = 1.0D;
            gridBagConstraints31.gridx = 1;
            
            GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
            gridBagConstraints40.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints40.gridy = 4;
            gridBagConstraints40.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints40.gridx = 0;
            
            GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
            gridBagConstraints41.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints41.gridy = 4;
            gridBagConstraints41.weightx = 1.0;
            gridBagConstraints41.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints41.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints41.gridwidth = 2;
            gridBagConstraints41.weighty = 1.0D;
            gridBagConstraints41.gridx = 1;
            
            GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
            gridBagConstraints50.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints50.gridy = 5;
            gridBagConstraints50.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints50.gridx = 0;
            
            GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
            gridBagConstraints51.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints51.gridy = 5;
            gridBagConstraints51.gridwidth = 2;
            gridBagConstraints51.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints51.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints51.weightx = 1.0D;
            gridBagConstraints51.weighty = 1.0D;
            gridBagConstraints51.gridx = 1;
            
            GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
            gridBagConstraints60.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints60.gridy = 6;
            gridBagConstraints60.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints60.gridx = 0;
            
            GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
            gridBagConstraints61.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints61.gridy = 6;
            gridBagConstraints61.weightx = 1.0;
            gridBagConstraints61.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints61.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints61.gridwidth = 2;
            gridBagConstraints61.weighty = 1.0D;
            gridBagConstraints61.gridx = 1;
            
            GridBagConstraints gridBagConstraints70 = new GridBagConstraints();
            gridBagConstraints70.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints70.gridy = 7;
            gridBagConstraints70.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints70.gridx = 0;
            
            GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
            gridBagConstraints71.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints71.gridy = 7;
            gridBagConstraints71.weightx = 1.0;
            gridBagConstraints71.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints71.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints71.gridwidth = 2;
            gridBagConstraints71.weighty = 1.0D;
            gridBagConstraints71.gridx = 1;
            
            GridBagConstraints gridBagConstraints80 = new GridBagConstraints();
            gridBagConstraints80.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints80.gridy = 8;
            gridBagConstraints80.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints80.gridx = 0;
            
            GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
            gridBagConstraints81.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints81.gridy = 8;
            gridBagConstraints81.weightx = 1.0;
            gridBagConstraints81.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints81.insets = new java.awt.Insets(2, 2, 2, 2);
            gridBagConstraints81.gridwidth = 2;
            gridBagConstraints81.weighty = 1.0D;
            gridBagConstraints81.gridx = 1;

            modelFileLabel = new JLabel();
            modelFileLabel.setText("Enter the Model file name:");
            
            modelFileTypeLabel = new JLabel();
            modelFileTypeLabel.setText("Enter the Model file type:");
            
            logicalModelLabel = new JLabel();
            logicalModelLabel.setText("Enter the 'Logical Model' package name:");
            
            dataModelLabel = new JLabel();
            dataModelLabel.setText("Enter the 'Data Model' package name:");
            
            includePackageLabel = new JLabel();
            includePackageLabel.setText("Enter the 'Include Package' regex:");
            
            excludePackageLabel = new JLabel();
            excludePackageLabel.setText("Enter the 'Exclude Package' regex:");
            
            excludeNameLabel = new JLabel();
            excludeNameLabel.setText("Enter the 'Exclude Class Name' regex:");
            
            excludeNamespaceLabel = new JLabel();
            excludeNamespaceLabel.setText("Enter the 'Exclude Namespace' regex:");
            
            modelSettingsPanel = new JPanel();
            modelSettingsPanel.setLayout(new GridBagLayout());
            modelSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Define Model Properties",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, null, PortalLookAndFeel.getPanelLabelColor()));
            
            modelSettingsPanel.add(modelFileLabel, gridBagConstraints10);
            modelSettingsPanel.add(getModelFileField(), gridBagConstraints11);
            modelSettingsPanel.add(getModelFilePathButton(), gridBagConstraints12);
            modelSettingsPanel.add(modelFileTypeLabel, gridBagConstraints20);
            modelSettingsPanel.add(getModelFileTypeField(), gridBagConstraints21);
            modelSettingsPanel.add(logicalModelLabel, gridBagConstraints30);
            modelSettingsPanel.add(getLogicalModelField(), gridBagConstraints31);
            modelSettingsPanel.add(dataModelLabel, gridBagConstraints40);
            modelSettingsPanel.add(getDataModelField(), gridBagConstraints41);
            modelSettingsPanel.add(includePackageLabel, gridBagConstraints50);
            modelSettingsPanel.add(getIncludePackageField(), gridBagConstraints51);
            modelSettingsPanel.add(excludePackageLabel, gridBagConstraints60);
            modelSettingsPanel.add(getExcludePackageField(), gridBagConstraints61);
            modelSettingsPanel.add(excludeNameLabel, gridBagConstraints70);
            modelSettingsPanel.add(getExcludeNameField(), gridBagConstraints71);
            modelSettingsPanel.add(excludeNamespaceLabel, gridBagConstraints80);
            modelSettingsPanel.add(getExcludeNamespaceField(), gridBagConstraints81);             
            
            modelSettingsPanel.validate();
		}
		return modelSettingsPanel;
	}
	
	/**
	 * This method initializes loggingSettingsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getCodeGenSettingsPanel() {
		if (codeGenSettingsPanel == null) {

			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints10.gridy = 1;
			gridBagConstraints10.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints10.gridx = 0;

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.weighty = 1.0D;
			gridBagConstraints11.weightx = 1.0D;  
			gridBagConstraints11.gridwidth = 2;

			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints20.gridy = 2;
			gridBagConstraints20.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints20.gridx = 0;            

			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints21.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints21.gridy = 2;
			gridBagConstraints21.gridx = 1;
			gridBagConstraints21.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints21.gridwidth = 2;
			gridBagConstraints21.weighty = 1.0D;
			gridBagConstraints21.weightx = 1.0D;  

			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints30.gridy = 3;
			gridBagConstraints30.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints30.gridx = 0;

			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints31.gridy = 3;
			gridBagConstraints31.weightx = 1.0;
			gridBagConstraints31.gridwidth = 2;
			gridBagConstraints31.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints31.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints31.weighty = 1.0D;
			gridBagConstraints31.gridx = 1;

			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
			gridBagConstraints40.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints40.gridy = 4;
			gridBagConstraints40.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints40.gridx = 0;

			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints41.gridy = 4;
			gridBagConstraints41.weightx = 1.0;
			gridBagConstraints41.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints41.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints41.gridwidth = 2;
			gridBagConstraints41.weighty = 1.0D;
			gridBagConstraints41.gridx = 1;

			GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
			gridBagConstraints50.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints50.gridy = 5;
			gridBagConstraints50.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints50.gridx = 0;

			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints51.gridy = 5;
			gridBagConstraints51.weightx = 1.0;
			gridBagConstraints51.gridwidth = 2;
			gridBagConstraints51.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints51.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints51.weighty = 1.0D;
			gridBagConstraints51.gridx = 1;

			GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
			gridBagConstraints60.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints60.gridy = 6;
			gridBagConstraints60.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints60.gridx = 0;

			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints61.gridy = 6;
			gridBagConstraints61.weightx = 1.0;
			gridBagConstraints61.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints61.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints61.gridwidth = 2;
			gridBagConstraints61.weighty = 1.0D;
			gridBagConstraints61.gridx = 1;

			GridBagConstraints gridBagConstraints70 = new GridBagConstraints();
			gridBagConstraints70.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints70.gridy = 7;
			gridBagConstraints70.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints70.gridx = 0;

			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
			gridBagConstraints71.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints71.gridy = 7;
			gridBagConstraints71.weightx = 1.0;
			gridBagConstraints71.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints71.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints71.gridwidth = 2;
			gridBagConstraints71.weighty = 1.0D;
			gridBagConstraints71.gridx = 1;

			GridBagConstraints gridBagConstraints80 = new GridBagConstraints();
			gridBagConstraints80.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints80.gridy = 8;
			gridBagConstraints80.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints80.gridx = 0;

			GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
			gridBagConstraints81.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints81.gridy = 8;
			gridBagConstraints81.weightx = 1.0;
			gridBagConstraints81.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints81.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints81.gridwidth = 2;
			gridBagConstraints81.weighty = 1.0D;
			gridBagConstraints81.gridx = 1;
			
			GridBagConstraints gridBagConstraints90 = new GridBagConstraints();
			gridBagConstraints90.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints90.gridy = 9;
			gridBagConstraints90.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints90.gridx = 0;

			GridBagConstraints gridBagConstraints91 = new GridBagConstraints();
			gridBagConstraints91.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints91.gridy = 9;
			gridBagConstraints91.weightx = 1.0;
			gridBagConstraints91.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints91.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints91.gridwidth = 2;
			gridBagConstraints91.weighty = 1.0D;
			gridBagConstraints91.gridx = 1;
			
			GridBagConstraints gridBagConstraints100 = new GridBagConstraints();
			gridBagConstraints100.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints100.gridy = 10;
			gridBagConstraints100.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints100.gridx = 0;

			GridBagConstraints gridBagConstraints101 = new GridBagConstraints();
			gridBagConstraints101.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints101.gridy = 10;
			gridBagConstraints101.weightx = 1.0;
			gridBagConstraints101.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints101.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints101.gridwidth = 2;
			gridBagConstraints101.weighty = 1.0D;
			gridBagConstraints101.gridx = 1;
			
			GridBagConstraints gridBagConstraints110 = new GridBagConstraints();
			gridBagConstraints110.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints110.gridy = 11;
			gridBagConstraints110.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints110.gridx = 0;

			GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
			gridBagConstraints111.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints111.gridy = 11;
			gridBagConstraints111.weightx = 1.0;
			gridBagConstraints111.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints111.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints111.gridwidth = 2;
			gridBagConstraints111.weighty = 1.0D;
			gridBagConstraints111.gridx = 1;

		    validateLogicalModelLabel = new JLabel();
		    validateLogicalModelLabel.setText("Validate Logical Model?");

		    validateModelMappingLabel = new JLabel();
		    validateModelMappingLabel.setText("Validate Model Mapping?");

		    validateGmeTagsLabel = new JLabel();
		    validateGmeTagsLabel.setText("Validate GME Tags?");
		    
		    generateBeansLabel = new JLabel();
		    generateBeansLabel.setText("Generate domain Java Beans?");

		    generateHibernateMappingLabel = new JLabel();
		    generateHibernateMappingLabel.setText("Generate Hibernate Mapping Files?");

		    generateCastorMappingLabel = new JLabel();
		    generateCastorMappingLabel.setText("Generate Castor Mapping files?");

		    generateXsdLabel = new JLabel();
		    generateXsdLabel.setText("Generate XSD's?");

		    generateXsdWithGmeTagsLabel = new JLabel();
		    generateXsdWithGmeTagsLabel.setText("Generate XSD's with GME tags?");
		    
		    generateXsdWithPermissibleValuesLabel = new JLabel();
		    generateXsdWithPermissibleValuesLabel.setText("Generate XSD's with Permissible Values?");
    
		    generateWsddLabel = new JLabel();
		    generateWsddLabel.setText("Generate WSDD?");

		    generateHibernateValidatorLabel = new JLabel();
		    generateHibernateValidatorLabel.setText("Generate Hibernate Validator?");

		    codeGenSettingsPanel = new JPanel();
		    codeGenSettingsPanel.setLayout(new GridBagLayout());
		    codeGenSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Define Code Generation Properties",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, PortalLookAndFeel.getPanelLabelColor()));

		    codeGenSettingsPanel.add(validateLogicalModelLabel, gridBagConstraints10);
		    codeGenSettingsPanel.add(getValidateLogicalModelCheckBox(), gridBagConstraints11);
		    codeGenSettingsPanel.add(validateModelMappingLabel, gridBagConstraints20);
			codeGenSettingsPanel.add(getValidateModelMappingCheckBox(), gridBagConstraints21);
			codeGenSettingsPanel.add(validateGmeTagsLabel, gridBagConstraints30);
			codeGenSettingsPanel.add(getValidateGmeTagsCheckBox(), gridBagConstraints31);
			codeGenSettingsPanel.add(generateBeansLabel, gridBagConstraints40);
			codeGenSettingsPanel.add(getGenerateBeansCheckBox(), gridBagConstraints41);
			codeGenSettingsPanel.add(generateHibernateMappingLabel, gridBagConstraints50);
			codeGenSettingsPanel.add(getGenerateHibernateMappingCheckBox(), gridBagConstraints51);
			codeGenSettingsPanel.add(generateCastorMappingLabel, gridBagConstraints60);
			codeGenSettingsPanel.add(getGenerateCastorMappingCheckBox(), gridBagConstraints61);
			codeGenSettingsPanel.add(generateXsdLabel, gridBagConstraints70);
			codeGenSettingsPanel.add(getGenerateXsdCheckBox(), gridBagConstraints71);
			codeGenSettingsPanel.add(generateXsdWithGmeTagsLabel, gridBagConstraints80);
			codeGenSettingsPanel.add(generateXsdWithGmeTagsCheckBox(), gridBagConstraints81);   
			codeGenSettingsPanel.add(generateXsdWithPermissibleValuesLabel, gridBagConstraints90);
			codeGenSettingsPanel.add(getGenerateXsdWithPermissibleValuesCheckBox(), gridBagConstraints91);
			codeGenSettingsPanel.add(generateWsddLabel, gridBagConstraints100);
			codeGenSettingsPanel.add(getGenerateWsddLabelCheckBox(), gridBagConstraints101);
			codeGenSettingsPanel.add(generateHibernateValidatorLabel, gridBagConstraints110);
			codeGenSettingsPanel.add(getGenerateHibernateValidatorCheckBox(), gridBagConstraints111);
			
		    codeGenSettingsPanel.validate();
		}
		return codeGenSettingsPanel;
	}
	
	/**
	 * This method initializes dbConnectionSettingsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getDbConnectionSettingsPanel() {
		if (dbConnectionSettingsPanel == null) {
			
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints10.gridy = 1;
			gridBagConstraints10.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints10.gridx = 0;

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.weighty = 1.0D;
			gridBagConstraints11.weightx = 1.0D;  
			gridBagConstraints11.gridwidth = 2;

			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints20.gridy = 2;
			gridBagConstraints20.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints20.gridx = 0;            

			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints21.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints21.gridy = 2;
			gridBagConstraints21.gridx = 1;
			gridBagConstraints21.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints21.gridwidth = 2;
			gridBagConstraints21.weighty = 1.0D;
			gridBagConstraints21.weightx = 1.0D;  

			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints30.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints30.gridy = 3;
			gridBagConstraints30.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints30.gridx = 0;
			gridBagConstraints30.gridwidth = 3;

			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
			gridBagConstraints40.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints40.gridy = 4;
			gridBagConstraints40.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints40.gridx = 0;

			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints41.gridy = 4;
			gridBagConstraints41.weightx = 1.0;
			gridBagConstraints41.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints41.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints41.gridwidth = 2;
			gridBagConstraints41.weighty = 1.0D;
			gridBagConstraints41.gridx = 1;

			GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
			gridBagConstraints50.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints50.gridy = 5;
			gridBagConstraints50.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints50.gridx = 0;

			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints51.gridy = 5;
			gridBagConstraints51.weightx = 1.0;
			gridBagConstraints51.gridwidth = 2;
			gridBagConstraints51.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints51.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints51.weighty = 1.0D;
			gridBagConstraints51.gridx = 1;

			GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
			gridBagConstraints60.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints60.gridy = 6;
			gridBagConstraints60.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints60.gridx = 0;

			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints61.gridy = 6;
			gridBagConstraints61.weightx = 1.0;
			gridBagConstraints61.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints61.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints61.gridwidth = 2;
			gridBagConstraints61.weighty = 1.0D;
			gridBagConstraints61.gridx = 1;

			GridBagConstraints gridBagConstraints70 = new GridBagConstraints();
			gridBagConstraints70.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints70.gridy = 7;
			gridBagConstraints70.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints70.gridx = 0;

			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
			gridBagConstraints71.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints71.gridy = 7;
			gridBagConstraints71.weightx = 1.0;
			gridBagConstraints71.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints71.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints71.gridwidth = 2;
			gridBagConstraints71.weighty = 1.0D;
			gridBagConstraints71.gridx = 1;

			GridBagConstraints gridBagConstraints80 = new GridBagConstraints();
			gridBagConstraints80.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints80.gridy = 8;
			gridBagConstraints80.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints80.gridx = 0;

			GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
			gridBagConstraints81.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints81.gridy = 8;
			gridBagConstraints81.weightx = 1.0;
			gridBagConstraints81.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints81.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints81.gridwidth = 2;
			gridBagConstraints81.weighty = 1.0D;
			gridBagConstraints81.gridx = 1;

		    useJndiBasedConnectionLabel = new JLabel();
			useJndiBasedConnectionLabel.setText("Use a JNDI-based Database Connection?");

		    dbJndiUrlLabel = new JLabel();
		    dbJndiUrlLabel.setText("Database JNDI URL:");

		    dbConnectionUrlLabel = new JLabel();
		    dbConnectionUrlLabel.setText("Database connection URL:");
		    
		    dbUsernameLabel = new JLabel();
		    dbUsernameLabel.setText("Database username:");

		    dbPasswordLabel = new JLabel();
		    dbPasswordLabel.setText("Database password:");

		    dbDriverLabel = new JLabel();
		    dbDriverLabel.setText("Database driver:");

		    dbDialectLabel = new JLabel();
		    dbDialectLabel.setText("Database dialect");

		    dbConnectionSettingsPanel = new JPanel();
		    dbConnectionSettingsPanel.setLayout(new GridBagLayout());
		    dbConnectionSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Define Database Connection Properties",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, PortalLookAndFeel.getPanelLabelColor()));
			
		    dbConnectionSettingsPanel.add(useJndiBasedConnectionLabel, gridBagConstraints10);
		    dbConnectionSettingsPanel.add(getUseJndiBasedConnectionCheckBox(), gridBagConstraints11);
		    dbConnectionSettingsPanel.add(dbJndiUrlLabel, gridBagConstraints20);
		    dbConnectionSettingsPanel.add(getDbJndiUrlField(), gridBagConstraints21);
		    dbConnectionSettingsPanel.add(new JLabel(blankLine), gridBagConstraints30);//blank spacer line
		    dbConnectionSettingsPanel.add(dbConnectionUrlLabel, gridBagConstraints40);
		    dbConnectionSettingsPanel.add(getDbConnectionUrlField(), gridBagConstraints41);
		    dbConnectionSettingsPanel.add(dbUsernameLabel, gridBagConstraints50);
		    dbConnectionSettingsPanel.add(getDbUsernameField(), gridBagConstraints51);
		    dbConnectionSettingsPanel.add(dbPasswordLabel, gridBagConstraints60);
		    dbConnectionSettingsPanel.add(getDbPasswordField(), gridBagConstraints61);
		    dbConnectionSettingsPanel.add(dbDriverLabel, gridBagConstraints70);
		    dbConnectionSettingsPanel.add(getDbDriverField(), gridBagConstraints71);
		    dbConnectionSettingsPanel.add(dbDialectLabel, gridBagConstraints80);
		    dbConnectionSettingsPanel.add(getDbDialectField(), gridBagConstraints81);   
			
		    dbConnectionSettingsPanel.validate();
		}
		return dbConnectionSettingsPanel;
	}
	
	/**
	 * This method initializes csmDbConnectionSettingsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getCsmDbConnectionSettingsPanel() {
		if (csmDbConnectionSettingsPanel == null) {
			
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints10.gridy = 1;
			gridBagConstraints10.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints10.gridx = 0;

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.weighty = 1.0D;
			gridBagConstraints11.weightx = 1.0D;  
			gridBagConstraints11.gridwidth = 2;

			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints20.gridy = 2;
			gridBagConstraints20.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints20.gridx = 0;            

			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints21.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints21.gridy = 2;
			gridBagConstraints21.gridx = 1;
			gridBagConstraints21.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints21.gridwidth = 2;
			gridBagConstraints21.weighty = 1.0D;
			gridBagConstraints21.weightx = 1.0D;  

			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints30.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints30.gridy = 3;
			gridBagConstraints30.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints30.gridx = 0;
			gridBagConstraints30.gridwidth = 3;

			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
			gridBagConstraints40.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints40.gridy = 4;
			gridBagConstraints40.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints40.gridx = 0;

			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints41.gridy = 4;
			gridBagConstraints41.weightx = 1.0;
			gridBagConstraints41.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints41.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints41.gridwidth = 2;
			gridBagConstraints41.weighty = 1.0D;
			gridBagConstraints41.gridx = 1;

			GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
			gridBagConstraints50.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints50.gridy = 5;
			gridBagConstraints50.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints50.gridx = 0;

			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints51.gridy = 5;
			gridBagConstraints51.weightx = 1.0;
			gridBagConstraints51.gridwidth = 2;
			gridBagConstraints51.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints51.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints51.weighty = 1.0D;
			gridBagConstraints51.gridx = 1;

			GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
			gridBagConstraints60.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints60.gridy = 6;
			gridBagConstraints60.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints60.gridx = 0;

			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints61.gridy = 6;
			gridBagConstraints61.weightx = 1.0;
			gridBagConstraints61.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints61.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints61.gridwidth = 2;
			gridBagConstraints61.weighty = 1.0D;
			gridBagConstraints61.gridx = 1;

			GridBagConstraints gridBagConstraints70 = new GridBagConstraints();
			gridBagConstraints70.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints70.gridy = 7;
			gridBagConstraints70.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints70.gridx = 0;

			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
			gridBagConstraints71.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints71.gridy = 7;
			gridBagConstraints71.weightx = 1.0;
			gridBagConstraints71.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints71.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints71.gridwidth = 2;
			gridBagConstraints71.weighty = 1.0D;
			gridBagConstraints71.gridx = 1;

			GridBagConstraints gridBagConstraints80 = new GridBagConstraints();
			gridBagConstraints80.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints80.gridy = 8;
			gridBagConstraints80.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints80.gridx = 0;

			GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
			gridBagConstraints81.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints81.gridy = 8;
			gridBagConstraints81.weightx = 1.0;
			gridBagConstraints81.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints81.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints81.gridwidth = 2;
			gridBagConstraints81.weighty = 1.0D;
			gridBagConstraints81.gridx = 1;

		    csmUseJndiBasedConnectionLabel = new JLabel();
			csmUseJndiBasedConnectionLabel.setText("Use a JNDI-based CSM Database Connection?");

		    csmDbJndiUrlLabel = new JLabel();
		    csmDbJndiUrlLabel.setText("Enter CSM Database JNDI URL:");

		    csmDbConnectionUrlLabel = new JLabel();
		    csmDbConnectionUrlLabel.setText("Enter CSM Database connection URL:");
		    
		    csmDbUsernameLabel = new JLabel();
		    csmDbUsernameLabel.setText("Enter CSM Database username:");

		    csmDbPasswordLabel = new JLabel();
		    csmDbPasswordLabel.setText("Enter CSM Database password:");

		    csmDbDriverLabel = new JLabel();
		    csmDbDriverLabel.setText("Enter CSM Database driver:");

		    csmDbDialectLabel = new JLabel();
		    csmDbDialectLabel.setText("Enter CSM Database dialect");

		    csmDbConnectionSettingsPanel = new JPanel();
		    csmDbConnectionSettingsPanel.setLayout(new GridBagLayout());
		    csmDbConnectionSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Define Common Security Module (CSM) Database Connection Properties",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, PortalLookAndFeel.getPanelLabelColor()));
			
		    csmDbConnectionSettingsPanel.add(csmUseJndiBasedConnectionLabel, gridBagConstraints10);
		    csmDbConnectionSettingsPanel.add(getCsmUseJndiBasedConnectionCheckBox(), gridBagConstraints11);
		    csmDbConnectionSettingsPanel.add(csmDbJndiUrlLabel, gridBagConstraints20);
		    csmDbConnectionSettingsPanel.add(getCsmDbJndiUrlField(), gridBagConstraints21);
		    csmDbConnectionSettingsPanel.add(new JLabel(blankLine), gridBagConstraints30);//blank spacer line
		    csmDbConnectionSettingsPanel.add(csmDbConnectionUrlLabel, gridBagConstraints40);
		    csmDbConnectionSettingsPanel.add(getCsmDbConnectionUrlField(), gridBagConstraints41);
		    csmDbConnectionSettingsPanel.add(csmDbUsernameLabel, gridBagConstraints50);
		    csmDbConnectionSettingsPanel.add(getCsmDbUsernameField(), gridBagConstraints51);
		    csmDbConnectionSettingsPanel.add(csmDbPasswordLabel, gridBagConstraints60);
		    csmDbConnectionSettingsPanel.add(getCsmDbPasswordField(), gridBagConstraints61);
		    csmDbConnectionSettingsPanel.add(csmDbDriverLabel, gridBagConstraints70);
		    csmDbConnectionSettingsPanel.add(getCsmDbDriverField(), gridBagConstraints71);
		    csmDbConnectionSettingsPanel.add(csmDbDialectLabel, gridBagConstraints80);
		    csmDbConnectionSettingsPanel.add(getCsmDbDialectField(), gridBagConstraints81);   
			
		    csmDbConnectionSettingsPanel.validate();
		}
		return csmDbConnectionSettingsPanel;
	}
	
//	/**
//	 * This method initializes loggingSettingsPanel	
//	 * 	
//	 * @return javax.swing.JPanel	
//	 */
//	private JPanel getClmSettingsPanel() {
//		if (loggingSettingsPanel == null) {
//			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
//			gridBagConstraints23.gridx = 0;
//			gridBagConstraints23.fill = GridBagConstraints.BOTH;
//			gridBagConstraints23.gridy = 0;
//			GridBagConstraints gridBagConstraints = new GridBagConstraints();
//			gridBagConstraints.gridx = 1;
//			gridBagConstraints.fill = GridBagConstraints.BOTH;
//			gridBagConstraints.weightx = 1.0D;
//			gridBagConstraints.weighty = 1.0D;
//			gridBagConstraints.gridy = 0;
//			loggingSettingsPanel = new JPanel();
//			loggingSettingsPanel.setLayout(new GridBagLayout());
//			loggingSettingsPanel.add(getServiceStylePanel(), gridBagConstraints);
//			loggingSettingsPanel.add(getResourceOptionsConfigPanel(), gridBagConstraints23);
//		}
//		return loggingSettingsPanel;
//	}
	
	/**
	 * This method initializes loggingSettingsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getCaGridAuthSettingsPanel() {
		if (caGridAuthSettingsPanel == null) {
		
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints10.gridy = 1;
			gridBagConstraints10.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints10.gridx = 0;

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.weighty = 1.0D;
			gridBagConstraints11.weightx = 1.0D;  
			gridBagConstraints11.gridwidth = 2;

			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints20.gridy = 2;
			gridBagConstraints20.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints20.gridx = 0;            

			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints21.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints21.gridy = 2;
			gridBagConstraints21.gridx = 1;
			gridBagConstraints21.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints21.gridwidth = 2;
			gridBagConstraints21.weighty = 1.0D;
			gridBagConstraints21.weightx = 1.0D;  

			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints30.gridy = 3;
			gridBagConstraints30.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints30.gridx = 0;

			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints31.gridy = 3;
			gridBagConstraints31.weightx = 1.0;
			gridBagConstraints31.gridwidth = 2;
			gridBagConstraints31.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints31.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints31.weighty = 1.0D;
			gridBagConstraints31.gridx = 1;

			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
			gridBagConstraints40.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints40.gridy = 4;
			gridBagConstraints40.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints40.gridx = 0;

			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints41.gridy = 4;
			gridBagConstraints41.weightx = 1.0;
			gridBagConstraints41.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints41.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints41.gridwidth = 2;
			gridBagConstraints41.weighty = 1.0D;
			gridBagConstraints41.gridx = 1;

			GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
			gridBagConstraints50.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints50.gridy = 5;
			gridBagConstraints50.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints50.gridx = 0;

			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints51.gridy = 5;
			gridBagConstraints51.weightx = 1.0;
			gridBagConstraints51.gridwidth = 2;
			gridBagConstraints51.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints51.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints51.weighty = 1.0D;
			gridBagConstraints51.gridx = 1;

			GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
			gridBagConstraints60.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints60.gridy = 6;
			gridBagConstraints60.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints60.gridx = 0;

			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints61.gridy = 6;
			gridBagConstraints61.weightx = 1.0;
			gridBagConstraints61.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints61.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints61.gridwidth = 2;
			gridBagConstraints61.weighty = 1.0D;
			gridBagConstraints61.gridx = 1;

			GridBagConstraints gridBagConstraints70 = new GridBagConstraints();
			gridBagConstraints70.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints70.gridy = 7;
			gridBagConstraints70.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints70.gridx = 0;

			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
			gridBagConstraints71.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints71.gridy = 7;
			gridBagConstraints71.weightx = 1.0;
			gridBagConstraints71.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints71.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints71.gridwidth = 2;
			gridBagConstraints71.weighty = 1.0D;
			gridBagConstraints71.gridx = 1;

			GridBagConstraints gridBagConstraints80 = new GridBagConstraints();
			gridBagConstraints80.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints80.gridy = 8;
			gridBagConstraints80.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints80.gridx = 0;

			GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
			gridBagConstraints81.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints81.gridy = 8;
			gridBagConstraints81.weightx = 1.0;
			gridBagConstraints81.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints81.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints81.gridwidth = 2;
			gridBagConstraints81.weighty = 1.0D;
			gridBagConstraints81.gridx = 1;

			enableCaGridLoginModuleLabel = new JLabel();
			enableCaGridLoginModuleLabel.setText("Enable caGrid Login Module?");

			caGridLoginModuleNameLabel = new JLabel();
			caGridLoginModuleNameLabel.setText("Enter caGrid Login Module Name:");

			caGridAuthSvcUrlLabel = new JLabel();
			caGridAuthSvcUrlLabel.setText("Enter caGrid Authentication Service URL:");
		    
			caGridDorianSvcUrlLabel = new JLabel();
			caGridDorianSvcUrlLabel.setText("Enter caGrid Dorian Service URL:");

			enableCsmLoginModuleLabel = new JLabel();
			enableCsmLoginModuleLabel.setText("Enable CSM Login Module?");

			sdkGridLoginSvcNameLabel = new JLabel();
			sdkGridLoginSvcNameLabel.setText("Enter SDK Grid Login Service Name:");

			sdkGridLoginSvcUrlLabel = new JLabel();
			sdkGridLoginSvcUrlLabel.setText("Enter SDK Grid Login Service URL:");

		    caGridAuthSettingsPanel = new JPanel();
		    caGridAuthSettingsPanel.setLayout(new GridBagLayout());
		    caGridAuthSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Define caGrid Authentication Properties",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, PortalLookAndFeel.getPanelLabelColor()));
		    
		    caGridAuthSettingsPanel.add(enableCaGridLoginModuleLabel, gridBagConstraints10);
		    caGridAuthSettingsPanel.add(getEnableCaGridLoginModuleCheckBox(), gridBagConstraints11);
		    caGridAuthSettingsPanel.add(caGridLoginModuleNameLabel, gridBagConstraints20);
		    caGridAuthSettingsPanel.add(getCaGridLoginModuleNameField(), gridBagConstraints21);
		    caGridAuthSettingsPanel.add(caGridAuthSvcUrlLabel, gridBagConstraints30);
		    caGridAuthSettingsPanel.add(getCaGridAuthSvcUrlField(), gridBagConstraints31);
		    caGridAuthSettingsPanel.add(caGridDorianSvcUrlLabel, gridBagConstraints40);
		    caGridAuthSettingsPanel.add(getCaGridDorianSvcUrlField(), gridBagConstraints41);
		    
		    caGridAuthSettingsPanel.add(new JLabel(blankLine), gridBagConstraints50);//blank spacer line
		    
		    caGridAuthSettingsPanel.add(enableCsmLoginModuleLabel, gridBagConstraints60);
		    caGridAuthSettingsPanel.add(getEnableCsmLoginModuleCheckBox(), gridBagConstraints61);
		    caGridAuthSettingsPanel.add(sdkGridLoginSvcNameLabel, gridBagConstraints70);
		    caGridAuthSettingsPanel.add(getSdkGridLoginSvcNameField(), gridBagConstraints71);
		    caGridAuthSettingsPanel.add(sdkGridLoginSvcUrlLabel, gridBagConstraints80);
		    caGridAuthSettingsPanel.add(getSdkGridLoginSvcUrlField(), gridBagConstraints81);
			
		    caGridAuthSettingsPanel.validate();
		}
		return caGridAuthSettingsPanel;
	}

	/**
	 * This method initializes appServerSettingsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getAppServerSettingsPanel() {
		if (appServerSettingsPanel == null) {
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints10.gridy = 1;
			gridBagConstraints10.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints10.gridx = 0;

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.weighty = 1.0D;
			gridBagConstraints11.weightx = 1.0D;  
			gridBagConstraints11.gridwidth = 2;

			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints20.gridy = 2;
			gridBagConstraints20.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints20.gridx = 0;            

			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints21.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints21.gridy = 2;
			gridBagConstraints21.gridx = 1;
			gridBagConstraints21.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints21.gridwidth = 2;
			gridBagConstraints21.weighty = 1.0D;
			gridBagConstraints21.weightx = 1.0D;  

//			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
//			gridBagConstraints30.anchor = java.awt.GridBagConstraints.WEST;
//			gridBagConstraints30.gridy = 3;
//			gridBagConstraints30.insets = new java.awt.Insets(2, 2, 2, 2);
//			gridBagConstraints30.gridx = 0;
//
//			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
//			gridBagConstraints31.fill = java.awt.GridBagConstraints.HORIZONTAL;
//			gridBagConstraints31.gridy = 3;
//			gridBagConstraints31.weightx = 1.0;
//			gridBagConstraints31.gridwidth = 2;
//			gridBagConstraints31.anchor = java.awt.GridBagConstraints.WEST;
//			gridBagConstraints31.insets = new java.awt.Insets(2, 2, 2, 2);
//			gridBagConstraints31.weighty = 1.0D;
//			gridBagConstraints31.gridx = 1;
//
//			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
//			gridBagConstraints40.anchor = java.awt.GridBagConstraints.WEST;
//			gridBagConstraints40.gridy = 4;
//			gridBagConstraints40.insets = new java.awt.Insets(2, 2, 2, 2);
//			gridBagConstraints40.gridx = 0;
//
//			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
//			gridBagConstraints41.fill = java.awt.GridBagConstraints.HORIZONTAL;
//			gridBagConstraints41.gridy = 4;
//			gridBagConstraints41.weightx = 1.0;
//			gridBagConstraints41.anchor = java.awt.GridBagConstraints.WEST;
//			gridBagConstraints41.insets = new java.awt.Insets(2, 2, 2, 2);
//			gridBagConstraints41.gridwidth = 2;
//			gridBagConstraints41.weighty = 1.0D;
//			gridBagConstraints41.gridx = 1;
//
//			GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
//			gridBagConstraints50.anchor = java.awt.GridBagConstraints.WEST;
//			gridBagConstraints50.gridy = 5;
//			gridBagConstraints50.insets = new java.awt.Insets(2, 2, 2, 2);
//			gridBagConstraints50.gridx = 0;
//
//			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
//			gridBagConstraints51.fill = java.awt.GridBagConstraints.HORIZONTAL;
//			gridBagConstraints51.gridy = 5;
//			gridBagConstraints51.weightx = 1.0;
//			gridBagConstraints51.gridwidth = 2;
//			gridBagConstraints51.anchor = java.awt.GridBagConstraints.WEST;
//			gridBagConstraints51.insets = new java.awt.Insets(2, 2, 2, 2);
//			gridBagConstraints51.weighty = 1.0D;
//			gridBagConstraints51.gridx = 1;
//
//			GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
//			gridBagConstraints60.anchor = java.awt.GridBagConstraints.WEST;
//			gridBagConstraints60.gridy = 6;
//			gridBagConstraints60.insets = new java.awt.Insets(2, 2, 2, 2);
//			gridBagConstraints60.gridx = 0;
//
//			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
//			gridBagConstraints61.fill = java.awt.GridBagConstraints.HORIZONTAL;
//			gridBagConstraints61.gridy = 6;
//			gridBagConstraints61.weightx = 1.0;
//			gridBagConstraints61.anchor = java.awt.GridBagConstraints.WEST;
//			gridBagConstraints61.insets = new java.awt.Insets(2, 2, 2, 2);
//			gridBagConstraints61.gridwidth = 2;
//			gridBagConstraints61.weighty = 1.0D;
//			gridBagConstraints61.gridx = 1;
//
//			GridBagConstraints gridBagConstraints70 = new GridBagConstraints();
//			gridBagConstraints70.anchor = java.awt.GridBagConstraints.WEST;
//			gridBagConstraints70.gridy = 7;
//			gridBagConstraints70.insets = new java.awt.Insets(2, 2, 2, 2);
//			gridBagConstraints70.gridx = 0;
//
//			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
//			gridBagConstraints71.fill = java.awt.GridBagConstraints.HORIZONTAL;
//			gridBagConstraints71.gridy = 7;
//			gridBagConstraints71.weightx = 1.0;
//			gridBagConstraints71.anchor = java.awt.GridBagConstraints.WEST;
//			gridBagConstraints71.insets = new java.awt.Insets(2, 2, 2, 2);
//			gridBagConstraints71.gridwidth = 2;
//			gridBagConstraints71.weighty = 1.0D;
//			gridBagConstraints71.gridx = 1;
//
//			GridBagConstraints gridBagConstraints80 = new GridBagConstraints();
//			gridBagConstraints80.anchor = java.awt.GridBagConstraints.WEST;
//			gridBagConstraints80.gridy = 8;
//			gridBagConstraints80.insets = new java.awt.Insets(2, 2, 2, 2);
//			gridBagConstraints80.gridx = 0;
//
//			GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
//			gridBagConstraints81.fill = java.awt.GridBagConstraints.HORIZONTAL;
//			gridBagConstraints81.gridy = 8;
//			gridBagConstraints81.weightx = 1.0;
//			gridBagConstraints81.anchor = java.awt.GridBagConstraints.WEST;
//			gridBagConstraints81.insets = new java.awt.Insets(2, 2, 2, 2);
//			gridBagConstraints81.gridwidth = 2;
//			gridBagConstraints81.weighty = 1.0D;
//			gridBagConstraints81.gridx = 1;
				
			serverTypeLabel = new JLabel();
			serverTypeLabel.setText("Select Server Type:");

			serverUrlLabel = new JLabel();
			serverUrlLabel.setText("Enter Server URL:");

			appServerSettingsPanel = new JPanel();
			appServerSettingsPanel.setLayout(new GridBagLayout());
			appServerSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Define Application Server Properties",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, PortalLookAndFeel.getPanelLabelColor()));
		    
			appServerSettingsPanel.add(serverTypeLabel, gridBagConstraints10);
			appServerSettingsPanel.add(getServerTypeComboBox(), gridBagConstraints11);
			appServerSettingsPanel.add(serverUrlLabel, gridBagConstraints20);
			appServerSettingsPanel.add(getServerUrlField(), gridBagConstraints21);
			
			appServerSettingsPanel.validate();
		}
		return appServerSettingsPanel;
	}
	
	/**
	 * This method initializes advancedSettingsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getAdvancedSettingsPanel() {
		if (advancedSettingsPanel == null) {
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints10.gridy = 1;
			gridBagConstraints10.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints10.gridx = 0;

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.weighty = 1.0D;
			gridBagConstraints11.weightx = 1.0D;  
			gridBagConstraints11.gridwidth = 2;

			cachePathLabel = new JLabel();
			cachePathLabel.setText("Enter EHCache Path:");

			advancedSettingsPanel = new JPanel();
			advancedSettingsPanel.setLayout(new GridBagLayout());
			advancedSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Define Advanced Properties",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, PortalLookAndFeel.getPanelLabelColor()));
		    
			advancedSettingsPanel.add(cachePathLabel, gridBagConstraints10);
			advancedSettingsPanel.add(getCachePathField(), gridBagConstraints11);
			
			advancedSettingsPanel.validate();

		}
		return advancedSettingsPanel;
	}

	/**
	 * This method initializes lifetimeResource	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getLifetimeResource() {
		if (lifetimeResource == null) {
			lifetimeResource = new JCheckBox();
			lifetimeResource.setToolTipText("add support for the WS-Lifetime specification");
			lifetimeResource.setHorizontalAlignment(SwingConstants.LEADING);
			lifetimeResource.setSelected(false);
//			lifetimeResource.setText(IntroduceConstants.INTRODUCE_LIFETIME_RESOURCE);
			lifetimeResource.setText("LifeTimeResource Text");
			lifetimeResource.setHorizontalTextPosition(SwingConstants.TRAILING);
			lifetimeResource.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkResourcePropertyOptions();
				}
			});
		}
		return lifetimeResource;
	}


	/**
	 * This method initializes persistantResource	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getPersistantResource() {
		if (persistantResource == null) {
			persistantResource = new JCheckBox();
			persistantResource.setToolTipText("enables resources to save state to disk and recover from disk");
			persistantResource.setSelected(false);
//			persistantResource.setText(IntroduceConstants.INTRODUCE_PERSISTENT_RESOURCE);
			persistantResource.setText("PersistantResource Text");
			persistantResource.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkResourcePropertyOptions();
				}
			});
		}
		return persistantResource;
	}


	/**
	 * This method initializes notificationResource	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getNotificationResource() {
		if (notificationResource == null) {
			notificationResource = new JCheckBox();
			notificationResource.setSelected(false);
			notificationResource.setToolTipText("add support for WS-Notification");
//			notificationResource.setText(IntroduceConstants.INTRODUCE_NOTIFICATION_RESOURCE);
			notificationResource.setText("NotificationResource Text");
			notificationResource.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkResourcePropertyOptions();
				}
			});
		}
		return notificationResource;
	}


	/**
	 * This method initializes secureResource	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getSecureResource() {
		if (secureResource == null) {
			secureResource = new JCheckBox();
			secureResource.setSelected(false);
			secureResource.setToolTipText("enables security to be added to this resource type");
//			secureResource.setText(IntroduceConstants.INTRODUCE_SECURE_RESOURCE);
			secureResource.setText("SecureResource");
			secureResource.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkResourcePropertyOptions();
				}
			});
		}
		return secureResource;
	}


	/**
	 * This method initializes resourceProperty	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getResourceProperty() {
		if (resourceProperty == null) {
			resourceProperty = new JCheckBox();
			resourceProperty.setSelected(true);
			resourceProperty.setToolTipText("enables access/query/set operations for the resource properties");
			resourceProperty.setText("resource property access");
			resourceProperty.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkResourcePropertyOptions();
				}
			});
		}
		return resourceProperty;
	}
	
	private void checkResourcePropertyOptions() {
		
		getLifetimeResource().setEnabled(true);
		getPersistantResource().setEnabled(true);
		getNotificationResource().setEnabled(true);
		getSecureResource().setEnabled(true);
		getResourceProperty().setEnabled(false);

	}
	
	private Map<String,String> getInstallerPropsMap(){
		Map<String,String> installerPropsMap=new HashMap<String,String>();
		
		// Project properties
		installerPropsMap.put("PROJECT_NAME", getProjectNameField().getText());
		installerPropsMap.put("NAMESPACE_PREFIX", getNamespacePrefixField().getText());
		installerPropsMap.put("WEBSERVICE_NAME", getWebServiceNameField().getText());
		
		// Model properties
		installerPropsMap.put("MODEL_FILE_PATH", getModelFileField().getText());
		installerPropsMap.put("MODEL_FILE_TYPE", getModelFileTypeField().getSelectedItem().toString());
		installerPropsMap.put("LOGICAL_MODEL", getLogicalModelField().getText());
		installerPropsMap.put("DATA_MODEL", getDataModelField().getText());
		installerPropsMap.put("INCLUDE_PACKAGE", getIncludePackageField().getText());
		installerPropsMap.put("EXCLUDE_PACKAGE", getExcludePackageField().getText());
		installerPropsMap.put("EXCLUDE_NAME", getExcludeNameField().getText());
		installerPropsMap.put("EXCLUDE_NAMESPACE", getExcludeNamespaceField().getText());
		
		// Code Generation properties
		installerPropsMap.put("VALIDATE_LOGICAL_MODEL", Boolean.valueOf(validateLogicalModelCheckBox.isSelected()).toString() );
		installerPropsMap.put("VALIDATE_MODEL_MAPPING", Boolean.valueOf(validateModelMappingCheckBox.isSelected()).toString() );
		installerPropsMap.put("VALIDATE_MODEL_MAPPING", Boolean.valueOf(validateGmeTagsCheckBox.isSelected()).toString() );
		installerPropsMap.put("GENERATE_HIBERNATE_MAPPING", Boolean.valueOf(generateHibernateMappingCheckBox.isSelected()).toString() );
		installerPropsMap.put("GENERATE_BEANS", Boolean.valueOf(generateBeansCheckBox.isSelected()).toString() );
		installerPropsMap.put("GENERATE_CASTOR_MAPPING", Boolean.valueOf(generateCastorMappingCheckBox.isSelected()).toString() );
		installerPropsMap.put("GENERATE_XSD", Boolean.valueOf(generateXsdCheckBox.isSelected()).toString() );
		installerPropsMap.put("GENERATE_XSD_WITH_GME_TAGS", Boolean.valueOf(generateXsdWithGmeTagsCheckBox.isSelected()).toString() );
		installerPropsMap.put("GENERATE_XSD_WITH_PERMISSIBLE_VALUES", Boolean.valueOf(generateXsdWithPermissibleValuesCheckBox.isSelected()).toString() );
		installerPropsMap.put("GENERATE_WSDD", Boolean.valueOf(generateWsddCheckBox.isSelected()).toString() );
		installerPropsMap.put("GENERATE_HIBERNATE_VALIDATOR", Boolean.valueOf(generateHibernateValidatorCheckBox.isSelected()).toString() );
		
		// DB Connection properties
		installerPropsMap.put("USE_JNDI_BASED_CONNECTION", Boolean.valueOf(useJndiBasedConnectionCheckBox.isSelected()).toString() );
		installerPropsMap.put("DB_JNDI_URL", getDbJndiUrlField().getText());
		installerPropsMap.put("DB_CONNECTION_URL", getDbConnectionUrlField().getText());
		installerPropsMap.put("DB_USERNAME", getDbUsernameField().getText());
		installerPropsMap.put("DB_PASSWORD", getDbPasswordField().getText());
		installerPropsMap.put("DB_DRIVER", getDbDriverField().getText());
		installerPropsMap.put("DB_DIALECT", getDbDialectField().getText());
		
		// Writable API properties
		installerPropsMap.put("ENABLE_WRITABLE_API_EXTENSION", Boolean.valueOf(enableWritableApiExtensionCheckBox.isSelected()).toString() );
		installerPropsMap.put("DATABASE_TYPE", getDatabaseTypeField().getText());
		installerPropsMap.put("IDENTITY_GENERATOR_TAG", getIdentityGeneratorTagField().getText());
		installerPropsMap.put("CADSR_CONNECTION_URL", getCaDsrConnectionUrlField().getText());
		
		// Common Logging Module DB Connection properties
		installerPropsMap.put("ENABLE_COMMON_LOGGING_MODULE", Boolean.valueOf(enableCommonLoggingModuleCheckBox.isSelected()).toString() );
		installerPropsMap.put("CLM_PROJECT_NAME", getClmProjectName().getText());		
		installerPropsMap.put("CLM_DB_CONNECTION_URL", getClmDbConnectionUrlField().getText());
		installerPropsMap.put("CLM_DB_USERNAME", getClmDbUsernameField().getText());
		installerPropsMap.put("CLM_DB_PASSWORD", getClmDbPasswordField().getText());
		installerPropsMap.put("CLM_DB_DRIVER", getClmDbDriverField().getText());
		
		// Security properties
		installerPropsMap.put("ENABLE_SECURITY", Boolean.valueOf(enableSecurityCheckBox.isSelected()).toString() );
		installerPropsMap.put("ENABLE_INSTANCE_LEVEL_SECURITY", Boolean.valueOf(enableInstanceLevelSecurityCheckBox.isSelected()).toString() );
		installerPropsMap.put("ENABLE_ATTRIBUTE_LEVEL_SECURITY", Boolean.valueOf(enableAttributeLevelSecurityCheckBox.isSelected()).toString() );
		installerPropsMap.put("CSM_PROJECT_NAME", getCsmProjectNameField().getText());
		installerPropsMap.put("CACHE_PROTECTION_ELEMENTS", Boolean.valueOf(cacheProtectionElementsCheckBox.isSelected()).toString() );
		
		// CSM DB Connection properties
		installerPropsMap.put("CSM_USE_JNDI_BASED_CONNECTION", Boolean.valueOf(csmUseJndiBasedConnectionCheckBox.isSelected()).toString() );
		installerPropsMap.put("CSM_DB_JNDI_URL", getCsmDbJndiUrlField().getText());
		installerPropsMap.put("CSM_DB_CONNECTION_URL", getCsmDbConnectionUrlField().getText());
		installerPropsMap.put("CSM_DB_USERNAME", getCsmDbUsernameField().getText());
		installerPropsMap.put("CSM_DB_PASSWORD", getCsmDbPasswordField().getText());
		installerPropsMap.put("CSM_DB_DRIVER", getCsmDbDriverField().getText());
		installerPropsMap.put("CSM_DB_DIALECT", getCsmDbDialectField().getText());
		
		// caGrid Authentication properties
		installerPropsMap.put("ENABLE_GRID_LOGIN_MODULE", Boolean.valueOf(enableCaGridLoginModuleCheckBox.isSelected()).toString() );
		installerPropsMap.put("CAGRID_LOGIN_MODULE_NAME", getCaGridLoginModuleNameField().getText());
		installerPropsMap.put("CAGRID_AUTHENTICATION_SERVICE_URL", getCaGridAuthSvcUrlField().getText());
		installerPropsMap.put("CAGRID_DORIAN_SERVICE_URL", getCaGridDorianSvcUrlField().getText());
		
		installerPropsMap.put("ENABLE_CSM_LOGIN_MODULE", Boolean.valueOf(enableCsmLoginModuleCheckBox.isSelected()).toString() );
		installerPropsMap.put("SDK_GRID_LOGIN_SERVICE_NAME", getSdkGridLoginSvcNameField().getText());
		installerPropsMap.put("SDK_GRID_LOGIN_SERVICE_URL", getSdkGridLoginSvcUrlField().getText());
		
		// Application Server Properties
		installerPropsMap.put("SERVER_TYPE", getServerTypeComboBox().getSelectedItem().toString());
		installerPropsMap.put("SERVER_URL", getServerUrlField().getText());

		// Advanced Settings properties	
		installerPropsMap.put("CACHE_PATH", getCachePathField().getText());
		
	    // TODO

		return installerPropsMap;
	}
} 
