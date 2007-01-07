#ifndef __P_ERROR_H__
#define __P_ERROR_H__

#define P_DEBUG // temporary declaration -> should be moved to Makefile

#ifdef P_DEBUG
#define P_ASSERT( EXP, MSG ) if( !(EXP) ) { throw pException( MSG, #EXP, __FILE__, __LINE__ ); }
#else
#define P_ASSERT( EXP, MSG )
#endif

#include <string>

class pException
{
public:
    pException( const char * ms, const char * ex, const char * fil, int lin ) : 
        line( lin ), msg( ms ), exp( ex ), file( fil ) {}
    ~pException() {}
    const char * show()
    {
        static char buffer[256] = "";
        sprintf( buffer, "%s at %d> `%s` : %s", file.c_str(), line, exp.c_str(), msg.c_str() );
        return( buffer );
    }
private:
    int line;
    std::string msg, exp, file;
};

#endif // __P_ERROR_H__

