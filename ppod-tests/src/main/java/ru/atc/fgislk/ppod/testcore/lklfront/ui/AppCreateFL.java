package ru.atc.fgislk.ppod.testcore.lklfront.ui;

import ru.atc.fgislk.ppod.testcore.lklfront.services.AppConfig;
import ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.pages.*;

/**
 * Создание приложения и всех компонентов
 */
public class AppCreateFL {
    /**
     * Главная страницу
     */
    public PageMainPage pageMainPage;
    /**
     * Страница Формирование документа «Лесная декларация»
     */
    public PageFormationDocument pageFormationDocument;
    /**
     * Страница Лесохозяйственные объекты
     */
    public PageForestryObjects pageForestryObjects;
    /**
     * Страница Добавление объекта
     */
    public PageAddObject pageAddObject;
    /**
     * Страница загрузка вложений
     */
    public PageDownloadAttachments pageDownloadAttachments;
    /**
     * Старница подписания сформированных документов Step 4
     */
    public PageSign pageSign;

    /**
     * Страница отправки документов
     */
    public PageSendDocuments pageSendDocuments;
    public AppCreateFL() {
        this.pageMainPage = new PageMainPage(AppConfig.stend.getPpodLk().getLklFront());
        this.pageFormationDocument = new PageFormationDocument();
        this.pageForestryObjects = new PageForestryObjects();
        this.pageAddObject = new PageAddObject();
        this.pageDownloadAttachments = new PageDownloadAttachments();
        this.pageSign = new PageSign();
        this.pageSendDocuments = new PageSendDocuments();
    }
}
