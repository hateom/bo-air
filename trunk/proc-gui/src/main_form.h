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

private:
    Ui::MainWindow ui;
};

#endif // __MAIN_FORM_H__

