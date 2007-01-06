#include <stdio.h>
#include <string.h>
#include "p_output.h"
#include "p_utils.h"
#include "p_parser.h"
#include "p_map.h"

int main( int argc, char * argv[] )
{
    int file_in = 1;
    bool flag;
    pMap map;

    if( argc == 3 && strcmp( argv[1], "-s" ) == 0 )
    {
        pOut->set_silent( true );
        file_in = 2;
    }

    pOut->print( "%s", COL_DEF );
    if( argc == 1 || argc > 3 )
    {
        pOut->print( "!!! %sERROR%s> wrong parameters\nusage:\n\t%s (-s) <input_file>\nparams:\n\t-s\t silent mode\n\n", COL_RED, COL_DEF, argv[0] );
        return( -1 );
    }

    pOut->print( "%s>>> %susing input file <%s%s%s>... ", COL_BLU, COL_GRY, COL_DEF, COL_GRY, argv[file_in] );
    if( !p_utils::file_exists( argv[file_in] ) )
    {
        pOut->print( "%s[ %s!!%s ]\n!!! ERROR> file <%s> does not exists!\n", COL_GRY, COL_RED, COL_GRY, argv[file_in] );
        return( -2 );
    }
    else
    {
        pOut->print( "%s[ %sOK%s ]\n", COL_GRY, COL_GRN, COL_GRY );
    }

    pOut->print( "%s>>>%s processing input file and generating output.\n", COL_BLU, COL_GRY );
    flag = Parser->process( argv[file_in], &map );
    Parser->release();

    pOut->print( "\n%s>>> %sprocessing is finished [ %s%s%s ]\n", COL_BLU, COL_GRY, flag?COL_GRN:COL_RED, flag?"OK":"!!", COL_GRY );
    pOut->print( "%s>>> %scontact us: %stomasz.huczek@gmail.com%s, %sjasinski.andrzej@gmail.com%s.\n\n", COL_BLU, COL_GRY, COL_DEF, COL_GRY, COL_DEF, COL_GRY );

    return( 0 );
}

