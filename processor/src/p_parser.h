#ifndef __PPARSER_H__
#define __PPARSER_H__

#define Parser pParser::singleton()

class pParser
{
public:
    static pParser * singleton();

    pParser();
    ~pParser();

    bool process( const char * in_file );
    void release();
    
private:

};

#endif // __PPARSER_H__

