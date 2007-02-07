#include <cstdlib>
#include <ctime>
#include "p_app.h"
#include "p_error.h"

#include "p_cost_mgr.h"

int main( int argc, char * argv[] )
{
    pApp app;

    srand( time(NULL) );

    try
    {
        if( app.init( argc, argv ) != 0 )
        {
            pError( ">> initialization failed!" );
            throw pNull();
        }

        if( app.run() != 0 )
        {
            pError( ">> app.run() error!" );
            throw pNull();
        }

        app.free();
    }
    catch( pException & e )
    {
        pErrorMgr::print();
        printf( "!! Exception: %s\n", e.show() );
        return -1;
    }
    catch( pNull & p )
    {
        pErrorMgr::print();
        app.free();
        return -2;
    }
    catch( ... )
    {
        fprintf( stderr, "!! Unknown exception!\n" );
        return -3;
    }

    return 0;
}

