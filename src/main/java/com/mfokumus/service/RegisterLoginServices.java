package com.mfokumus.service;

import com.mfokumus.controller.RegisterController;
import com.mfokumus.controller.VkiController;
import com.mfokumus.dao.RegisterDao;
import com.mfokumus.dto.RegisterDto;
import com.mfokumus.dto.VkiDto;
import com.mfokumus.files.FilePathData;
import com.mfokumus.roles.ERoles;

import java.text.DecimalFormat;
import java.util.Scanner;

public class RegisterLoginServices {

    // Injection
    private RegisterController registerController = new RegisterController();
    private VkiController vkiController = new VkiController();
    private FilePathData filePathData = new FilePathData();

    // Eğer sistemde ilgili email ile kullanıcı varsa sisteme giriş yapsın
    // Eğer sistemde ilgili email yoksa register olsun

    private String[] allRoles() {
      /*  for ( String temp :ERoles.valueOf() ) {
        }*/
        return null;
    }

    // Register
    private RegisterDto register(){
        Scanner klavye = new Scanner(System.in);
        RegisterDto registerDto = new RegisterDto();
        String uNickname, uEmailAddress, uPassword, rolles;
        Long remainingNumber;
        Boolean isPassive;
        System.out.println("\n###REGISTER SAYSASINA HOSGELDINIZ");
        System.out.println("Takma adınızı giriniz");
        uNickname = klavye.nextLine();
        System.out.println("Emailinizi giriniz");
        uEmailAddress = klavye.nextLine();
        System.out.println("Sifrenizi giriniz");
        uPassword = klavye.nextLine();
        // default rol user olacak
        rolles = ERoles.USER.getValue().toString();
        remainingNumber = 5L;
        isPassive = true;
        ///////////////////
        registerDto.setuNickName(uNickname);
        registerDto.setuEmailAddress(uEmailAddress);
        registerDto.setuPassword(uPassword);
        registerDto.setRolles(rolles);
        registerDto.setRemainingNumber(remainingNumber);
        registerDto.setPassive(isPassive);
        // CREATE
        registerController.create(registerDto);
        return registerDto;
    }

    // LOGIN
    public RegisterDto login() {
        Scanner klavye = new Scanner(System.in);
        RegisterDto registerDto = new RegisterDto();
        String uEmailAddress, uPassword;
        Long remaingNumber = 0L;
        System.out.println("\n----LOGIN SAYSASINA HOSGELDINIZ----");
        System.out.println("Emailinizi giriniz");
        uEmailAddress = klavye.nextLine();
        System.out.println("Sifrenizi giriniz");
        uPassword = klavye.nextLine();

        // Email Find
        RegisterDto registerEmailFind = registerController.findByEmail(uEmailAddress);
        // Kullanıcı yoksa kayıt olsun ve login sayfasına geri donsun.
        if (registerEmailFind == null) {
            // eğer kullanıcı yoksa kayıt olsun
            register();
            // Kayıt olduktan sonra Login sayfasına geri dön
            login();
        } else {
            // Eğer Kullanıcı Pasifse giris yapmasın
            if (registerEmailFind.getPassive() == false) {
                System.out.println("Üyeliğiniz Pasif edilmiştir sisteme giriş yapamazsınız");
                System.out.println("Lütfen admin'e başvurunuz.");
                System.exit(0);
            }

            // Database kaydedilmis decode edilmis sifre karsilastirmak
            RegisterDao registerDao=new RegisterDao();
            String firstValue=uPassword;
            String rawPassword=registerDao.generatebCryptPasswordEncoder(firstValue);
            boolean result=registerDao.matchbCryptPassword(firstValue,registerEmailFind.getuPassword());

            // Eğer kullanıcı varsa sisteme giriş yapsın    uPassword.equals(registerEmailFind.getuPassword()
            if (uEmailAddress.equals(registerEmailFind.getuEmailAddress()) && registerDao.matchbCryptPassword(firstValue,registerEmailFind.getuPassword()) ) {
                adminProcess(registerEmailFind);
            } else {
                // Kullanıcının kalan hakkı
                remaingNumber = registerEmailFind.getRemainingNumber();
                remaingNumber--;
                registerEmailFind.setRemainingNumber(remaingNumber);
                System.out.println("Kalan Hakkınız: " + registerEmailFind.getRemainingNumber());
                System.out.println("Sifreniz veya Emailiniz yanlış girdiniz");
                // Kalan Hak Database Eksilt
                registerController.updateRemaing(remaingNumber, registerEmailFind);
                // File Loglama yapsın
                filePathData.logFileWriter(uEmailAddress, uPassword);
                // Sisteme giriş hakkım kalmazsa
                if (remaingNumber == 0) {
                    System.out.println("Giriş hakkınız kalmadı Hesanız Bloke oldu");
                    System.out.println("Admine Başvuru yapınız");
                    System.exit(0);
                } else if (remaingNumber >= 1) {
                    login();
                }
            } //end else
        }
        return registerDto;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private void adminProcess(RegisterDto registerDto) {
        Scanner klavye = new Scanner(System.in);
        while (true) {
            System.out.println("\n----MFO SISTEMINE HOSGELDINIZ----");
            System.out.println("Lütfen Seçiminizi Yapınız");
            System.out.println("0-) Ana sayfa\n1-) VKI (Vücut Kitle İndexi) Hesaplama\n2-) VKI Bilgi Silme Islemi");
            System.out.println("3-) Üye Listele\n4-) Üye Ekle\n5-) Üye Bul(ID)\n6-) Üye Bul (Email)");
            System.out.println("7-) Üye Güncelle\n8-) Üye Sil\n9-) Giriş Logları\n10-) Rolünüz");
            System.out.println("11-) Dosya Ekle\n12-) Dosya Listele\n13-) Dosya Sil");
            System.out.println("14-) Dosya Bilgileri  \n15-) Cikis Yap");
            int chooise = klavye.nextInt();
            switch (chooise) {
                case 0:
                    System.out.println("Home Page");
                    specialHomePage();
                    break;
                ////////////////////////////////////////////////////////////////////////////////////
                case 1:
                    VkiDto vkiDto = vki_hesaplama(registerDto);
                    vki_database(vkiDto);
                    vkiLimitCheck(vkiDto);
                    backToHomePage();
                    break;
                ////////////////////////////////////////////////////////////////////////////////////
                case 2:
                    System.out.println("---VKI Bilgi Silme Islemine Hosgeldiniz---");
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue())) {
                        vkiList();
                        Long vkiDtoDelete = vkiDelete(registerDto.getId());
                        System.out.println(vkiDtoDelete);
                        backToHomePage();
                    } else {
                        System.out.println("Rolünüz: " +registerDto.getRolles()+ " Bilgi silmek icin yetkiniz yoktur."+
                                "\nSilmek istiyorsanız admin ile iletisime geciniz.");
                        backToHomePage();
                    }
                    break;
                ////////////////////////////////////////////////////////////////////////////////////
                case 3:
                    System.out.println("Listeleme");
                    memberList();
                    break;
                case 4:
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue())) {
                        System.out.println("Oluşturma");
                        RegisterDto registerDtoCreate = memberCreate();
                        System.out.println(registerDtoCreate);
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 5:
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue())) {
                        memberList();
                        System.out.println("ID'e göre Bulma");
                        RegisterDto registerDtoFindId = memberFindById();
                        /*if(registerDto.getId()==registerDtoFindId.getId()){
                            System.out.println(registerController.findById(registerDto.getId()));
                        }
                        else{
                            System.out.println(registerDtoFindId);
                        }*/
                        System.out.println(registerDtoFindId);
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 6:
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue())) {
                        memberList();
                        System.out.println("Email'e göre bulma");
                        RegisterDto registerDtoFindEmail = memberfindEmail();
                        System.out.println(registerDtoFindEmail);
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 7:
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue())) {
                        memberList();
                        System.out.println("Güncelleme");
                        RegisterDto registerDtoUpdate = memberUpdate();
                        System.out.println(registerDtoUpdate);
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 8:
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue())) {
                        memberList();
                        System.out.println("Silme");
                        RegisterDto registerDtoDelete = memberDelete();
                        System.out.println(registerDtoDelete);
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 9:
                    logFile();
                    break;
                case 10:
                    System.out.println("Rolünüz: " + userRoles(registerDto.getRolles()));
                    break;
                case 11:
                    System.out.println("Dosya Ekleme");
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue())) {
                        specialFileCreateData();
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 12:
                    System.out.println("Dosya Listeleme");
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue())) {
                        fileListData();
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 13:
                    System.out.println("Dosya Silme");
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue()) ) {
                        fileDeleteData();
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 14:
                    System.out.println("Dosya Bilgileri");
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue())) {
                        fileInformation();
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 15:
                    logout();
                    break;
                default:
                    System.out.println("Lütfen belirtilen aralıkta sayı giriniz");
                    break;
            } //end switch
        } //end while
    } //end method adminProcess

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // METHOD
    // VKİ HESAPLAMA
    public VkiDto vki_hesaplama(RegisterDto registerDto){
        Scanner klavye = new Scanner(System.in);
        VkiDto vkiDto = new VkiDto();
        vkiDto.setUserId(registerDto.getId());
        System.out.println("\n ---- Vucut Kitle Indexi Hesaplama Bolumune Hosgeldiniz Saglikli Gunler Dileriz ----");
        System.out.println("\nVücut kitle indeksi (VKİ), bir kişinin boy ve kilo ölçümlerine dayanarak vücuttaki yağ oranını hesaplamak için" +
                "\nkullanılan bir ölçüttür. \n\nVKİ değerleri aşağıdaki kategorilere ayrılır:");
        System.out.println("1-) 18.5 ve altı: Ideal Kilo Alti (Zayif)" +
                "\n" +
                "2-) 18.5 ile 24.9 arasi: Ideal Kilo" +
                "\n" +
                "3-) 25 ile 29.9 arasi: Kilolu" +
                "\n" +
                "4-) 30 ile 34.9 arasi: 1. Derece Obezite" +
                "\n" +
                "5-) 35 ile 39.9 arasi: 2. Derece Obezite" +
                "\n" +
                "6-) 40 ve üzeri: 3. Derece Obezite");
        System.out.println("\nAsagida bulunan boy ve kilo bilgilerinizi girerek vucut " +
                "kitle idexi bilginizi ogrenebilirsiniz.");
        // Kilo girişi
        System.out.println("\nLütfen kilonuzu kilgoram cinsinden giriniz (Ör:85,6)");
        vkiDto.setKilo(klavye.nextDouble());
        // Boy girişi
        System.out.println("Lütfen boyunuzu metre cinsinden giriniz (Ör:1,75)");
        vkiDto.setBoy(klavye.nextDouble());
        // Sonuc Hesapla
        Double sonuc = (vkiDto.getKilo() / (vkiDto.getBoy() * vkiDto.getBoy()));
        vkiDto.setVucutKitleIndex(sonuc);
        return vkiDto;
    }// end vki hesaplama
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VERILERI DATABASE'DE OLUSTURSUN
    // GET VKI ILE DATABASE KONTROLU YAPSIN YOKSA OLUSTURSUN VARSA UPDATE ETSIN
    private void vki_database(VkiDto vkiDto) {
        VkiDto vkiDto_db = vkiController.findByUserId(vkiDto.getUserId());
        if (vkiDto_db.getId()==null){
            vkiController.create(vkiDto);
        }else{
            vkiController.update(vkiDto_db.getId(),vkiDto);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VKI ALT LIMIT UST LIMIT 'e GORE SONUCU GOSTERSIN
    private void vkiLimitCheck (VkiDto vkiDto){
        final DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Boyunuz: "+ vkiDto.getBoy() + "\nKilonuz: " + vkiDto.getKilo());
        if (0<=vkiDto.getVucutKitleIndex() && vkiDto.getVucutKitleIndex()<18.5){
            System.out.println("\nVucut Kitle Indexsiniz: "+df.format(vkiDto.getVucutKitleIndex()) +
                    " => Ideal Kilonun Altindasiniz");
        } else if (18.5<=vkiDto.getVucutKitleIndex() && vkiDto.getVucutKitleIndex()<25) {
            System.out.println("Vucut Kitle Indexsiniz: "+df.format(vkiDto.getVucutKitleIndex())+
                    " => Ideal Kilodasiniz");
        }else if (25<=vkiDto.getVucutKitleIndex() && vkiDto.getVucutKitleIndex()<30) {
            System.out.println("Vucut Kitle Indexsiniz: "+df.format(vkiDto.getVucutKitleIndex())+
                    " => Ideal Kilonun Ustundesiniz (Fazla Kilolu)");
        }else if (30<=vkiDto.getVucutKitleIndex() && vkiDto.getVucutKitleIndex()<35) {
            System.out.println("Vucut Kitle Indexsiniz: "+df.format(vkiDto.getVucutKitleIndex())+
                    " => 1. Derece Obezite.");
        }else if (35<=vkiDto.getVucutKitleIndex() && vkiDto.getVucutKitleIndex()<40) {
            System.out.println("Vucut Kitle Indexsiniz: " + df.format(vkiDto.getVucutKitleIndex()) +
                    " => 2. Derece Obezite");
        }else{
            System.out.println("Vucut Kitle Indexsiniz: " + df.format(vkiDto.getVucutKitleIndex()) +
                    " => 3. Derece Obezite.");
        }
        System.out.println("--------------------------------------------------------------------");
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ANASAYFAYA DON
    private void backToHomePage(){
        System.out.println("Anasayfaya Donmek Icin ENTER Tusuna Basin");
        Scanner klavye = new Scanner(System.in);
        while (true){
            String result = klavye.nextLine();
            if (!result.equals("")) {
                System.out.println("Anasayfaya donmek icin lutfen enter tusuna basin");
            } else {
                System.out.println("Ansayfaya Yonlendiriliyorsunuz...");
                break;
            }
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // just member login
    private void specialHomePage() {
        System.out.println("Sadece Üyeler Bu sayfayı görebilir.");
    }
    // CRUD
    // USER LIST
    private void memberList() {
        registerController.list().forEach(System.out::println);
    }
    //////////////////////////////////////////////////////////////////////////
    // VKI LIST
    private void vkiList() {
        vkiController.list().forEach(System.out::println);
    }
    //////////////////////////////////////////////////////////////////////////
    // USER CREATE
    private RegisterDto memberCreate() {
        return register();
    }

    // USER Find Id
    private RegisterDto memberFindById() {
        System.out.println("Lütfen Bulmak istediğiniz ID giriniz");
        return registerController.findById(new Scanner(System.in).nextLong());
    }

    // USER Find Email
    private RegisterDto memberfindEmail() {
        System.out.println("Lütfen Bulmak istediğiniz email giriniz");
        return registerController.findByEmail(new Scanner(System.in).nextLine());
    }

    // USER Update
    private RegisterDto memberUpdate() {
        Scanner klavye = new Scanner(System.in);
        RegisterDto registerDto = new RegisterDto();
        String uNickName, uEmailAddress, uPassword, rolles;
        Long remainingNumber, id;
        Boolean isPassive;
        System.out.println("Güncellemek istediğiniz ID  giriniz");
        id = klavye.nextLong();
        // NOT: Scanner'da tam sayıdan sonra String Gelirse bir alt satıra geçiyor
        // bunu engellemenin yolu klavye.nextLine()
        klavye.nextLine();

        System.out.println("Güncellemek istediğiniz Takma adınızı giriniz");
        uNickName = klavye.nextLine();
        System.out.println("Güncellemek istediğiniz Emailinizi giriniz");
        uEmailAddress = klavye.nextLine();
        System.out.println("Güncellemek istediğiniz Sifrenizi giriniz");
        uPassword = klavye.nextLine();
        // default rol user olacak
        rolles = ERoles.USER.getValue().toString();
        System.out.println("Güncellemek istediğiniz hak sayısını giriniz");
        remainingNumber = klavye.nextLong();
        System.out.println("Güncellemek istediğiniz kullanıcı aktif/pasif");
        isPassive = true;
        ////////////////////////////////////////////////////////////////////
        registerDto.setId(id);
        registerDto.setuNickName(uNickName);
        registerDto.setuEmailAddress(uEmailAddress);
        registerDto.setuPassword(uPassword);
        registerDto.setRolles(rolles);
        registerDto.setRemainingNumber(remainingNumber);
        registerDto.setPassive(isPassive);
        return registerController.update(id, registerDto);
    }

    // USER Delete
    private RegisterDto memberDelete() {
        Scanner klavye = new Scanner(System.in);
        RegisterDto registerDto = new RegisterDto();
        Long id;
        System.out.println("Silmek istediğiniz ID'i giriniz");
        id = klavye.nextLong();
        registerDto.setId(id);
        return registerController.deleteById(registerDto);
    }
    /////////////////////////////////////////////////////////////////////////////////////////
    // User VKI Bilgi Delete
    private Long vkiDelete(Long id) {
        Scanner klavye = new Scanner(System.in);
        System.out.println("\nUser ID'niz: "+ id);
        System.out.println("Lutfen VKI bilgisini silmek istediginiz kullanıcının user id sini giriniz : ");
        Long user_id;
        while(true){
            user_id = klavye.nextLong();
            if (user_id!=null && user_id==id){  //Eger girilen input null degilse ve sistemdeki id ile aynıysa delete et
                return vkiController.deleteByUserID(id);
            }else {
                System.out.println("Yanlış veya eksik bir tuşlama yaptınız. Tekrar Giriniz!");
            }
        }
    }// end vkiDelete
    //////////////////////////////////////////////////////////////////////////////////////////////
    // LOGLAMA
    private void logFile() {
        filePathData.logFileReader();
    }

    // ROLES
    private String userRoles(String roles) {
        return roles;
    }

    // Logout
    private void logout() {
        //JOptionPane.
        System.out.println("Sistemden Çıkmak mı istiyor sunuz ? E/H");
        Scanner klavye = new Scanner(System.in);
        char result = klavye.nextLine().toLowerCase().charAt(0);
        if (result == 'e') {
            System.out.println("Sistemden Çıkış yapılıyor iyi günler dileriz.");
            System.exit(0);
        } else {
            System.out.println("Sistemden Çıkış yapılmadı");
        }
    } //end logout()

    // CREATE FILE
    private void specialFileCreateData() {
        Scanner klavye = new Scanner(System.in);
        System.out.println("Oluşturmak istediğiniz dosya adını giriniz");
        String fileName = klavye.nextLine();
        filePathData.specialFileCreate(fileName);
    }

    // File List , Information
    private void fileListData() {
        filePathData.fileList();
    }

    // File Delete
    private void fileDeleteData() {
        filePathData.fileIsDelete();
    }

    // File Information
    private void fileInformation() {
        filePathData.fileProperties();
    }

}


