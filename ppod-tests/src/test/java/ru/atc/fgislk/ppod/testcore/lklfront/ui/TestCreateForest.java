package ru.atc.fgislk.ppod.testcore.lklfront.ui;

import io.qameta.allure.Step;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import ru.atc.fgislk.ppod.testcore.common.RandomString;
import ru.atc.fgislk.ppod.testcore.common.enums.SenderDataEnum;
import ru.atc.fgislk.ppod.testcore.common.enums.TypeForestUseEnum;
import ru.atc.fgislk.ppod.testcore.common.enums.TypeForestUsers;
import ru.atc.fgislk.ppod.testcore.lklfront.services.AppConfig;
import ru.atc.fgislk.ppod.testcore.lklfront.services.ConnectDB;
import ru.atc.fgislk.ppod.testcore.lklfront.services.LklFront;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;

import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static ru.atc.fgislk.ppod.testcore.common.enums.TypeForestUseEnum.*;
import static ru.atc.fgislk.ppod.testcore.common.enums.TypeForestUsers.*;

public class TestCreateForest extends BaseTest {
    @Test(description = "Создание декларации с ФЛ", groups = {"sprint4"})
    public void createForestFL(@Optional("DEV") StendsDescriptionEnum stend) throws SQLException, ParseException, AWTException, InterruptedException {
        AppConfig.stend = stend;
        AppConfig.lklFront = new LklFront(stend);
        AppConfig.dataBase = new ConnectDB(stend);
        // AppConfig.camundaService = new CamundaStep(stend); Пока не работает

        AppCreateFL app = new AppCreateFL();
        app.pageMainPage.open();
        app.pageMainPage.selectProcess("Формирование лесных деклараций и отчетов об использовании лесов", "Формирование лесной декларации");
        // Добавим Виды использования лесов
        List<String> typeForestUse = new ArrayList<>();
        typeForestUse.add("Заготовка живицы");
        typeForestUse.add("Заготовка и сбор недревесных лесных ресурсов");

        formationDocument(app, PERSON, true, typeForestUse);
        app.pageForestryObjects.clickButtonAddObject();
        addObject(app, HARVESTING_WOOD);
        app.pageForestryObjects.clickButtonNext();
        downloadAttachments(app);
        sign(app);
        sendDocument(app);
    }

    private void sendDocument(AppCreateFL app) throws ParseException {
        app.pageSendDocuments.selectUser(SenderDataEnum.PERSON.getName());
        app.pageSendDocuments.setLastName("Иванов");
        app.pageSendDocuments.setFirstName("Иван");
        app.pageSendDocuments.setPatronymic("Иванович");
        app.pageSendDocuments.setFias();
        app.pageSendDocuments.selectDocumentType();
        app.pageSendDocuments.setSeries(RandomString.randomString(6));
        app.pageSendDocuments.setNumber(RandomString.randomInt(6));
        app.pageSendDocuments.setIssueName("Рога и копыта");
        app.pageSendDocuments.setIssueData(RandomString.randomDateRange("01.01.2000", "01.01.2020"));
        app.pageSendDocuments.setIssueCode(RandomString.randomInt(6));
        app.pageSendDocuments.setSnils(RandomString.randomInt(11));
        app.pageSendDocuments.setInn(RandomString.randomInt(12));
        app.pageSendDocuments.setEmail(RandomString.randomEmail());
        app.pageSendDocuments.clickButtonSend();
    }

    private void sign(AppCreateFL app) {
        app.pageSign.clickButtonSign();
        app.pageSign.formSelectSertificate.selectCertificate(AppConfig.certificate);
        app.pageSign.formSelectSertificate.clickButtonSign();
    }

    /**
     * Загрузка вложений
     * @param app приложение
     */
    @Step("Загрузка вложений")
    private void downloadAttachments(AppCreateFL app) throws AWTException {
        app.pageDownloadAttachments.clickButtonUpload();
        app.pageDownloadAttachments.formAttachment.selectDocumentType();
        app.pageDownloadAttachments.formAttachment.addFile("upload/Image_002.txt");
        app.pageDownloadAttachments.formAttachment.addSigFile("upload/Image_003.txt");
        app.pageDownloadAttachments.formAttachment.clickButtonSave();

        app.pageDownloadAttachments.clickButtonGenerateDoc();
    }

    /**
     * Добавление объекта
     * @param app приложение
     */
    @Step("Добавление объекта")
    private void addObject(AppCreateFL app, TypeForestUseEnum typeForestUseEnum) throws AWTException, InterruptedException {
        app.pageAddObject.blockAddObject.selectForestry();
        app.pageAddObject.blockAddObject.selectDistrictForestry();
        app.pageAddObject.blockAddObject.selectTract();
        app.pageAddObject.blockAddObject.setQuarter(RandomString.randomInt(6));
        app.pageAddObject.blockAddObject.setSelection(RandomString.randomInt(5));
        app.pageAddObject.blockAddObject.setCuttingNumber(RandomString.randomInt(4));
        app.pageAddObject.blockAddObject.clickDownloadObjectScheme();
        app.pageAddObject.blockAddObject.formAttachment.addFile("upload/Image_002.txt");
        app.pageAddObject.blockAddObject.formAttachment.addSigFile("upload/Image_003.txt");
        app.pageAddObject.blockAddObject.formAttachment.setScale(RandomString.randomInt(2));
        app.pageAddObject.blockAddObject.formAttachment.clickButtonUploadDoc();
        app.pageAddObject.blockAddObject.formAttachment.selectFileUpload();
        app.pageAddObject.blockAddObject.formAttachment.clickButtonSave();
        app.pageAddObject.blockTypeUse.selectTypeUse(typeForestUseEnum.getName());
        switch (typeForestUseEnum){
            case HARVESTING_WOOD:
            case HARVESTING_GALIPOT:
            case HARVESTING_WOOD_AND_GALIPOT:
                app.pageAddObject.blockTypeUse.setCuttingArea(RandomString.randomInt(4));
                app.pageAddObject.blockFellingForestPlantations.clickButtonAdd();
                app.pageAddObject.blockFellingForestPlantations.formFellingForestPlantations.selectFarm();
                app.pageAddObject.blockFellingForestPlantations.formFellingForestPlantations.selectBreed();
                app.pageAddObject.blockFellingForestPlantations.formFellingForestPlantations.selectFellingType();
                app.pageAddObject.blockFellingForestPlantations.formFellingForestPlantations.selectFellingShape();
                app.pageAddObject.blockFellingForestPlantations.formFellingForestPlantations.setVolume(RandomString.randomInt(3));
                app.pageAddObject.blockFellingForestPlantations.formFellingForestPlantations.selectUnit();
                app.pageAddObject.blockFellingForestPlantations.formFellingForestPlantations.clickButtonSave();
                break;
            case HARVESTING_OBJECT:
                app.pageAddObject.blockTypeUse.selectObjectName();
                app.pageAddObject.blockTypeUse.selectEvent();
                app.pageAddObject.blockTypeUse.setObjectArea(RandomString.randomCharInt(4));
                break;
            case OTHER_USAGE_TYPES:
                app.pageAddObject.blockTypeUse.selectTypesHarvestedesources();
                app.pageAddObject.blockTypeUse.setUsableArea(RandomString.randomInt(5));
                app.pageAddObject.blockTypeUse.selectUnits("м^3");
                app.pageAddObject.blockTypeUse.setScopeUse(RandomString.randomInt(4));
                break;
            case OTHER_USAGE_OBJECTS:
                app.pageAddObject.blockTypeUse.selectObjectNameOther();
                app.pageAddObject.blockTypeUse.selectEventOther();
                app.pageAddObject.blockTypeUse.setObjectAreaOther(RandomString.randomInt(4));
                app.pageAddObject.blockObjectCoordinates.clickAddCoordinates();
                app.pageAddObject.blockObjectCoordinates.formAddCoordinates.setLatitude(RandomString.randomInt(6));
                app.pageAddObject.blockObjectCoordinates.formAddCoordinates.setLongitude(RandomString.randomInt(6));
                app.pageAddObject.blockObjectCoordinates.formAddCoordinates.clickButtonSave();
                break;
        }
        app.pageAddObject.clickButtonSave();
    }

    /**
     * Формирование документа «Лесная декларация»
     * @param app приложение
     * @param representative представитель
     * @param typeForestUse виды использования лесов
     */
    @Step("Формирование документа «Лесная декларация»")
    private void formationDocument(AppCreateFL app, TypeForestUsers typeForestUser, Boolean representative, List<String> typeForestUse) throws ParseException {
        switch (typeForestUser) {
            case PERSON:
                setFL(app, representative);
                break;
            case ORG:
                setUL(app);
                break;
            case IP:
                setIP(app, representative);
                break;
        }

        app.pageFormationDocument.blockDeclarationData.selectSubjectRF();
        app.pageFormationDocument.blockDeclarationData.selectForestry();
        app.pageFormationDocument.blockDeclarationData.selectPublicAuthority();
        app.pageFormationDocument.blockDeclarationData.setNumberForestDeclaration(RandomString.randomInt(5));
        app.pageFormationDocument.blockDeclarationData.setDataValidityDeclaration(RandomString.randomDateRange("01.01.2020", "01.01.2021"), RandomString.randomDateRange("01.02.2021", "31.12.2022"));
        app.pageFormationDocument.blockDeclarationData.setTypeLeaseDocument();
        app.pageFormationDocument.blockDeclarationData.setNumberLeaseDocument(RandomString.randomInt(5));
        app.pageFormationDocument.blockDeclarationData.setDataLeaseDocument(RandomString.randomDateRange("01.01.2020", "01.01.2021"));

        app.pageFormationDocument.blockDeclarationData.setTypeForestUse(typeForestUse);

        app.pageFormationDocument.clickButtonNext();
    }

    private void setIP(AppCreateFL app, Boolean representative) throws ParseException {
        app.pageFormationDocument.selectUser(IP.getName());
        app.pageFormationDocument.blockDataPersonIP.setLastName("Иванов");
        app.pageFormationDocument.blockDataPersonIP.setFirstName("Иван");
        app.pageFormationDocument.blockDataPersonIP.setPatronymic("Иванович");
        if (representative) {
            app.pageFormationDocument.blockRepresentativeIP.setLastName("Петров");
            app.pageFormationDocument.blockRepresentativeIP.setFirstName("Петр");
            app.pageFormationDocument.blockRepresentativeIP.setPatronymic("Петрович");
            app.pageFormationDocument.blockRepresentativeIP.setPost("Представитель");
            app.pageFormationDocument.blockRepresentativeIP.setPhone(RandomString.randomInt(10));
            app.pageFormationDocument.blockRepresentativeIP.selectTypeCode();
            app.pageFormationDocument.blockRepresentativeIP.setDocNumber(RandomString.randomInt(5));
            app.pageFormationDocument.blockRepresentativeIP.setDataDoc(RandomString.randomDateRange("01.01.2020", "01.01.2021"));
        }
    }

    private void setUL(AppCreateFL app) throws ParseException {
        app.pageFormationDocument.selectUser(ORG.getName());
        app.pageFormationDocument.blockDataPersonUL.setOrgName("Рога и копыта");
        app.pageFormationDocument.blockDataPersonUL.setInn(RandomString.randomInt(10));
        app.pageFormationDocument.blockDataPersonUL.setLastName("Петров");
        app.pageFormationDocument.blockDataPersonUL.setFirstName("Петр");
        app.pageFormationDocument.blockDataPersonUL.setPatronymic("Петрович");
        app.pageFormationDocument.blockDataPersonUL.setPost("Представитель");
        app.pageFormationDocument.blockDataPersonUL.setPhone(RandomString.randomInt(10));
        app.pageFormationDocument.blockDataPersonUL.selectTypeCode();
        app.pageFormationDocument.blockDataPersonUL.setDocNumber(RandomString.randomInt(5));
        app.pageFormationDocument.blockDataPersonUL.setDataDoc(RandomString.randomDateRange("01.01.2020", "01.01.2021"));
    }

    private void setFL(AppCreateFL app, Boolean representative) throws ParseException {
        app.pageFormationDocument.selectUser(PERSON.getName());
        app.pageFormationDocument.blockDataPersonFL.setPersonLastName("Иванов");
        app.pageFormationDocument.blockDataPersonFL.setPersonFirstName("Иван");
        app.pageFormationDocument.blockDataPersonFL.setPersonPatronymic("Петрович");
        app.pageFormationDocument.blockDataPersonFL.selectPersonDocumentCode();
        app.pageFormationDocument.blockDataPersonFL.setPersonDocumentSeries(RandomString.randomCharInt(5));
        app.pageFormationDocument.blockDataPersonFL.setPersonDocumentNumber(RandomString.randomInt(5));
        app.pageFormationDocument.blockRepresentativeFL.setRepresentative(representative);
        if (representative) {
            app.pageFormationDocument.blockRepresentativeFL.setLastName("Петров");
            app.pageFormationDocument.blockRepresentativeFL.setFirstName("Петр");
            app.pageFormationDocument.blockRepresentativeFL.setPatronymic("Петрович");
            app.pageFormationDocument.blockRepresentativeFL.setPost("Представитель");
            app.pageFormationDocument.blockRepresentativeFL.setPhone(RandomString.randomInt(10));
            app.pageFormationDocument.blockRepresentativeFL.selectTypeCode();
            app.pageFormationDocument.blockRepresentativeFL.setDocNumber(RandomString.randomInt(5));
            app.pageFormationDocument.blockRepresentativeFL.setDataDoc(RandomString.randomDateRange("01.01.2020", "01.01.2021"));
        }
    }
}