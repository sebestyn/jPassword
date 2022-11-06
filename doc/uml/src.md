classDiagram
direction BT
class AES {

+ AES()

- int KEY_SIZE
- SecretKey key
- int T_LEN
- byte[] IV

+ init(MasterPassword) void
+ generateKey(MasterPassword) SecretKey
+ encrypt(String) String

- decode(String) byte[]

+ decrypt(String) String

- encode(byte[]) String
  }
  class App {

+ App()
  ~ Model model
+ runGUI() void
+ runConsole() void
  }
  class ConsoleApp {
+ ConsoleApp(Model)
  ~ BufferedReader reader
  ~ Model model
+ run() void

- auth() void
  }
  class Controller {

+ Controller(Model, View)
  }
  class Crypt {
+ Crypt()
+ encrypt(CryptType, String) String
+ init(MasterPassword) void
+ decrypt(CryptType, String) String
  }
  class CryptType {
  <<enumeration>>
+ CryptType()
+ NONE
+ SHA256
+ SEBI
+ AES
+ values() CryptType[]
+ valueOf(String) CryptType
  }
  class CryptoException {
  ~ CryptoException(String)
  }
  class DashboardController {
+ DashboardController(Model, View)
  ~ Model model
  ~ View view
  ~ DashboradPage dashboradPage
+ resetFolderItemSelectListener() void
  }
  class DashboradPage {
+ DashboradPage()
  ~ ScrollPane scrollFolderTree
  ~ JButton newFolderButton
  ~ PasswordInputPage passwordInputPage
  ~ JTable table
  ~ DataNotes dataNotes
  ~ JButton removeFolderButton
  ~ JTextField searchInput
  ~ JPanel rightPanel
  ~ JPanel leftPanel
  ~ JButton removeItemButton
  ~ JScrollPane scrollTable
  ~ DataPasswords dataPasswords
  ~ NoteInputPage noteInputPage
  ~ JTree folderTree
  ~ JButton newItemButton
+ getRemoveItemButton() JButton
+ showFolderListItem(Folder) void

- showTableTools() void

+ showPasswordsItem(HashSet~Password~) void
+ getFolderTree() JTree
+ getDataNotes() DataNotes
+ showTempOpenFileText() void
+ getPasswordInputPage() PasswordInputPage
+ getTable() JTable
+ removeRightPanel() void
+ getNoteInputPage() NoteInputPage
+ getDataPasswords() DataPasswords
+ getRemoveFolderButton() JButton
+ getNewFolderButton() JButton
+ getNewItemButton() JButton
+ getSearchInput() JTextField
+ showNewPasswordPage() void
+ showNewNotePage() void
+ showNotesItem(HashSet~Note~) void
  }
  class DataNotes {
+ DataNotes(HashSet~Note~)
  ~ ArrayList~Note~ notesList
  ~ HashSet~Note~ notes
+ getRowCount() int
+ getValueAt(int, int) Object
+ addNote(Note) void
+ getColumnCount() int
+ setValueAt(Object, int, int) void
+ getColumnName(int) String
+ isCellEditable(int, int) boolean
  }
  class DataPasswords {
+ DataPasswords(HashSet~Password~)
  ~ ArrayList~Password~ passwordsList
  ~ HashSet~Password~ passwords
+ isCellEditable(int, int) boolean
+ getRowCount() int
+ getColumnCount() int
+ addPassword(Password) void
+ getColumnName(int) String
+ getValueAt(int, int) Object
+ setValueAt(Object, int, int) void
  }
  class Folder {
+ Folder(String, Folder)

- HashSet~Folder~ folders
- HashSet~Note~ notes
- HashSet~Password~ passwords
- String forderName
- Folder parentFolder

+ getFolder(String) Folder
+ toString() String
+ addNote(Note) void
+ getParentFolder() Folder
+ removeNote(Note) void
+ makeEmpty() void
+ removePassword(Password) void
+ save(String) void
+ list(String) void
+ addPassword(Password) void
+ addFolder(Folder) void
+ getNotes() HashSet~Note~
+ searchNote(String) HashSet~Note~
+ getName() String
+ equals(Object) boolean
+ load(String) void
+ getPasswords() HashSet~Password~

- deleteDir(File) void

+ removeFolder(Folder) void
+ getFolders() HashSet~Folder~
+ searchPassword(String) HashSet~Password~
+ hashCode() int
  }
  class Global {
+ Global()
+ convertFolderToTreeNode(Folder) DefaultMutableTreeNode
  }
  class Global {
+ Global()
+ oneLineString(String) String
  }
  class LoginController {
+ LoginController(Model, View)
  ~ Model model
  ~ View view
  ~ LoginPage loginPage

- runRegistration() void
- runLogin() void
- runDashboard() void
  }
  class LoginPage {

+ LoginPage()
  ~ JTextField passwordInput
  ~ JButton loginButton
  ~ boolean registration
+ setToRegistration() void
+ getRegistration() boolean
+ getLoginButton() JButton
+ getPasswordInput() JTextField
  }
  class Main {
+ Main()
+ main(String[]) void
  }
  class MasterPassword {
+ MasterPassword(String, String)
+ MasterPassword(String)
  ~ CryptType cryptType
  ~ String value
  ~ String filePath
+ removePassword() void
+ savePassword() void
+ setValue(String) void
+ getValue() String
+ isInputEqualsHash(String, String) boolean
+ loadPassword() String
+ isAlreadyExist() boolean
  }
  class MenuController {
+ MenuController(Model, View, DashboardController)
  ~ Model model
  ~ MenuPanel menuPanel
  ~ DashboardController dashboardController
  ~ View view
  }
  class MenuPanel {
+ MenuPanel()
  ~ JMenuItem saveMenu
  ~ JMenuItem resetMenu
  ~ JMenuItem loadMenu
  ~ JRadioButtonMenuItem resetOn3InvalidLogin
+ getSaveMenu() JMenuItem
+ getResetOn3InvalidLogin() JRadioButtonMenuItem
+ getLoadMenu() JMenuItem
+ init(Settings) void
+ getResetMenu() JMenuItem
  }
  class Model {
+ Model()

- String mainFolderPath
- Folder mainFolder
  ~ Settings settings
- int loginAttemp
- MasterPassword masterPassword
- Folder actualFolder

+ need_to_regigster() boolean
+ getActualFolder() Folder
+ getMainFolderPath() String
+ setActualFolder(Folder) void
+ getSettings() Settings
+ getMasterPassword() MasterPassword
+ getMainFolder() Folder
+ register(String) void
+ loadData(String) void
+ login(String) String
+ factoryReset() void
+ saveData() void
  }
  class Note {
+ Note(String, String)
+ Note(CryptType, String, String)

- String name
- String note
- CryptType cryptType

+ getEncrypted() String
+ toString() String
+ setNote(String) void
+ getNote() String
+ equals(Object) boolean
+ hashCode() int
+ getName() String
+ setName(String) void
+ getCryptType() CryptType
  }
  class NoteInputPage {
+ NoteInputPage()
  ~ JTextField noteInput
  ~ JComboBox~String~ cryptType_list
  ~ JTextField nameInput
  ~ JButton saveButton
+ getSaveButton() JButton
+ getCryptType_list() JComboBox~String~
+ getNoteInput() JTextField
+ getNameInput() JTextField
  }
  class Password {
+ Password(CryptType, String, String, String)
+ Password(String, String)
+ Password(CryptType, String, String)

- String password
- String name
- String username
- CryptType cryptType

+ setUsername(String) void
+ getUsername() String
+ hashCode() int
+ getPassword() String
+ setName(String) void
+ toString() String
+ setPassword(String) void
+ getEncrypted() String
+ getCryptType() CryptType
+ getName() String
+ equals(Object) boolean
  }
  class PasswordInputPage {
+ PasswordInputPage()
  ~ JComboBox~String~ cryptType_list
  ~ JButton saveButton
  ~ JTextField nameInput
  ~ JTextField passwordInput
  ~ JTextField usernameInput
+ getCryptType_list() JComboBox~String~
+ getPasswordInput() JTextField
+ getNameInput() JTextField
+ getUsernameInput() JTextField
+ getSaveButton() JButton
  }
  class SEBI {
+ SEBI()
  ~ String key
+ decrypt(String) String
+ encrypt(String) String
+ init(MasterPassword) void
  }
  class SHA256 {
+ SHA256()
+ decrypt(String) String
+ encrypt(String) String

- toHexString(byte[]) String
- getSHA(String) byte[]
  }
  class Settings {

+ Settings(String)

- String filePath
- boolean factoryReset

+ getFactoryReset() boolean
+ save() void
+ setFactoryReset(boolean) void
+ load() void
  }
  class View {
+ View(Model)
  ~ LoginPage loginPage
  ~ DashboradPage dashboradPage
  ~ Model model
  ~ MenuPanel menuPanel
+ getMenuPanel() MenuPanel
+ getLoginPage() LoginPage
+ toggleDashboardPage(boolean, Folder) void
+ toggleMenuPanel(boolean) void
+ toggleLoginPage(boolean) void
+ clear() void
+ getDashboradPage() DashboradPage
  }

App ..>  ConsoleApp : «create»
App ..>  Controller : «create»
App ..>  Model : «create»
App "1" *--> "model 1" Model
App ..>  View : «create»
ConsoleApp ..>  Folder : «create»
ConsoleApp "1" *--> "model 1" Model
ConsoleApp ..>  Note : «create»
ConsoleApp ..>  Password : «create»
Controller ..>  LoginController : «create»
DashboardController "1" *--> "dashboradPage 1" DashboradPage
DashboardController "1" *--> "model 1" Model
DashboardController "1" *--> "view 1" View
DashboradPage "1" *--> "dataNotes 1" DataNotes
DashboradPage ..>  DataNotes : «create»
DashboradPage "1" *--> "dataPasswords 1" DataPasswords
DashboradPage ..>  DataPasswords : «create»
DashboradPage ..>  NoteInputPage : «create»
DashboradPage "1" *--> "noteInputPage 1" NoteInputPage
DashboradPage "1" *--> "passwordInputPage 1" PasswordInputPage
DashboradPage ..>  PasswordInputPage : «create»
DataNotes "1" *--> "notesList *" Note
DataPasswords "1" *--> "passwordsList *" Password
Folder ..>  Note : «create»
Folder "1" *--> "notes *" Note
Folder ..>  Password : «create»
Folder "1" *--> "passwords *" Password
LoginController ..>  DashboardController : «create»
LoginController "1" *--> "loginPage 1" LoginPage
LoginController ..>  MenuController : «create»
LoginController "1" *--> "model 1" Model
LoginController "1" *--> "view 1" View
Main ..>  App : «create»
MasterPassword "1" *--> "cryptType 1" CryptType
MenuController "1" *--> "dashboardController 1" DashboardController
MenuController "1" *--> "menuPanel 1" MenuPanel
MenuController "1" *--> "model 1" Model
MenuController "1" *--> "view 1" View
Model "1" *--> "mainFolder 1" Folder
Model ..>  Folder : «create»
Model "1" *--> "masterPassword 1" MasterPassword
Model ..>  MasterPassword : «create»
Model "1" *--> "settings 1" Settings
Model ..>  Settings : «create»
Note "1" *--> "cryptType 1" CryptType
Password "1" *--> "cryptType 1" CryptType
SHA256 ..>  CryptoException : «create»
View "1" *--> "dashboradPage 1" DashboradPage
View ..>  DashboradPage : «create»
View "1" *--> "loginPage 1" LoginPage
View ..>  LoginPage : «create»
View ..>  MenuPanel : «create»
View "1" *--> "menuPanel 1" MenuPanel
View "1" *--> "model 1" Model 
