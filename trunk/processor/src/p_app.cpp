#include <iostream>
#include "p_app.h"
#include "p_cfg_mgr.h"
#include "p_output.h"
#include "p_utils.h"
#include "p_error.h"
#include "p_map.h"
#include "p_tabu_search.h"
#include "p_cost_mgr.h"

pApp::pApp()
{
}

pApp::~pApp()
{
    free();
}

int pApp::init( int argc, char * argv[] )
{
    char * ptr;

    if( arg_valid( argc, argv ) != 0 ) 
    {
        std::cout << "usage: " << argv[0] << " [args]\n\nargs:\n\t--input=<input_file>\n\t--silent={1,0}\n\t--config=CFG_FILE\n\t--K=x\t\t\tK parameter\n\t--T=x\t\t\tT parameter\n\t--ALPHA=x\t\talpha parameter\n\n";
        return -1;
    }
    if( parse_arg( argc, argv ) != 0 )
    {
        pError( "wrong usage!" );
        return -2;
    }

    if( strcmp( pCfgMgr::get("silent"), "1" ) == 0 )
    {
        pOut->set_silent( true );
    }

    char * cfg;
    cfg = (char*)pCfgMgr::get( "config" );
    if( cfg != NULL && strcmp( cfg, "" ) )
    {
        if( pc::load( cfg ) != 0 )
        {
            pError( "could not load config file! <%s>", cfg );
        }
    }
    else
    {
        pc::set_default();
    }

    ptr = (char*)pCfgMgr::get( "input" );
    if( !p_utils::file_exists( ptr ) )
    {
        pError( "missing input file! <%s>", ptr );
        return -3;
    }
    
    return 0;
}

int pApp::arg_valid( int argc, char * argv[] )
{
    if( argc < 2 )
    {
        return -1;
    }

    return 0;
}

int pApp::parse_arg( int argc, char * argv[] )
{
    for( int i=1; i<argc; ++i )
    {
        if( argv[i][0] == '-' && argv[i][1] == '-' )
        {
            pCfgMgr::add_cfg( argv[i] );
        }
        else
        {
            return( -1 );
        }
    }

    return 0;
}

int pApp::run()
{
    if( Map->load( pCfgMgr::get( "input" ) ) != 0 )
    {
        pError( "cannot open map file! <%s>", pCfgMgr::get( "input" ) );
        return -1;
    }

    pTabuSearch ts;
    if( ts.init() != 0 )
    {
        pError( "tabu search initialization error!" );
        ts.free();
        return -2;
    }

    if( ts.exec() != 0 )
    {
        pError( "tabu search execution error!" );
        ts.free();
        return -3;
    }
    ts.free();

    return 0;
}

void pApp::free()
{
    Map->free();
    pCfgMgr::free();
}

