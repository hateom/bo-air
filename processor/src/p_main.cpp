#include "p_app.h"
#include "p_error.h"

int main( int argc, char * argv[] )
{
    try
    {
        pApp app;

        if( !app.init( argc, argv ) )
        {
            pError( ">> initialization failed!" );
            return -1;
        }

        if( !app.run() )
        {
            pError( ">> app.run() error!" );
            app.free();
            return -2;
        }

        app.free();
    }
    catch( pException & e )
    {
        pErrorMgr::print();
        e.show();
        return -3;
    }

    pErrorMgr::print();

    return 0;
}

