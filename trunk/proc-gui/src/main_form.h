#ifndef __MAIN_FORM_H__
#define __MAIN_FORM_H__

#include <QMainWindow>
#include "ui/ui_main_form.h"

class pgMainForm: public QMainWindow
{
    Q_OBJECT
public:
    pgMainForm();
    ~pgMainForm();

public slots:
    void on_process();

private:
    Ui::MainWindow ui;
};

#endif // __MAIN_FORM_H__

