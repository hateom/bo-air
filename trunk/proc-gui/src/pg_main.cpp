#include <QApplication>
#include "main_form.h"

int main( int argc, char * argv[] )
{
    QApplication app( argc, argv );
    pgMainForm mf;
    mf.show();
    return( app.exec() );
}

