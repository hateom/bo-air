#include <stdio.h>
#include "main_form.h"

#define MAX_BUF 255

pgMainForm::pgMainForm()
{
    ui.setupUi( this );

    connect( ui.pushButton, SIGNAL(clicked()), this, SLOT(on_process()) );
}

pgMainForm::~pgMainForm()
{
}

void pgMainForm::on_process()
{
    QString str = ui.textEdit->toPlainText();

    FILE * file = ::fopen( "./input_data", "wt" );
    ::fprintf( file, "%s", str.toAscii().constData() );
    ::fclose( file );

    FILE * pfile;
    int r_size;
    char buffer[MAX_BUF+1];

    pfile = popen( "./processor -s ./input_data", "r" );
    if( !pfile )
    {
        printf( "!!! Error opening pipe.\n" );
        return;
    }

    while( ( r_size = fread( buffer, 1, MAX_BUF, pfile ) ) > 0 )
    {
        buffer[r_size] = '\0';
        ui.textEdit_2->insertPlainText( tr(buffer) );
    }
    pclose( pfile );
}
