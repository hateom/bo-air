#ifndef __P_OUTPUT_H__
#define __P_OUTPUT_H__

#include <string>

#define COL_RED "\e[38m"
#define COL_BLU "\e[36m"
#define COL_GRN "\e[32m"
#define COL_DEF "\e[39m"
#define COL_GRY "\e[37m"

#define pOut pOutput::singleton()

class pOutput
{
public:
    static pOutput * singleton();

    pOutput();
    ~pOutput();

    void print( const char * str, ... );
    void print( std::string str );

    void set_silent( bool on );

private:
    bool silent;
};

#endif // __P_OUTPUT_H__

