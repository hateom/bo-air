#ifndef __P_APP_H__
#define __P_APP_H__

class pApp
{
public:
    pApp();
    ~pApp();

    int  init( int argc, char * argv[] );
    int  run();
    void free();

private:
    int parse_arg( int argc, char * argv[] );
    int arg_valid( int argc, char * argv[] );
};

#endif // __P_APP_H__

