
;(function()
{
    // CommonJS
    typeof (require) != 'undefined' ? SyntaxHighlighter = require('shCore').SyntaxHighlighter : null;

    function Brush()
    {
        var keywords =  'ISARRAY ERASE LBOUND UBOUND LET SET REM EMPTY NOTHING NULL TRUE FALSE DO LOOP FOR NEXT '+
                        'EACH IF END THEN ELSE SELECT CASE WHILE WEND ABS ASC ASCB ASCW CHR CHRB CHRW CBOOL CBYTE CDATE '+
                        'CDBL CINT CLNG CSNG CSTR DATESERIAL DATEVALUE HEX OCT FIX INT SGN TIMESERIAL TIMEVALUE DATE '+
                        'TIME DATESERIAL DATEVALUE DAY MONTH WEEKDAY YEAR HOUR MINUTE SECOND NOW TIMESERIAL TIMEVALUE '+
                        'DIM PRIVATE PUBLIC REDIM FUNCTION SUB ON ERROR ERR INPUTBOX MSGBOX ATN COS SIN TAN EXP LOG '+
                        'SQR RANDOMIZE RND MOD IS AND OR XOR EQV IMP CREATEOBJECT '+
                        'ISOBJECT OPTION EXPLICIT CALL INSTR INSTRB LEN LENB LCASE UCASE LEFT LEFTB '+
                        'MID MIDB RIGHT RIGHTB SPACE STRCOMP STRING LTRIM RTRIM TRIM ISDATE ISEMPTY ISNULL '+
                        'ISNUMERIC VARTYPE';

        var r = SyntaxHighlighter.regexLib;
        
        this.regexList = [
            { regex: r.multiLineDoubleQuotedString,                 css: 'string' },            // double quoted strings
            { regex: r.multiLineSingleQuotedString,                 css: 'string' },            // single quoted strings
            { regex: /`(?:\.|(\\\`)|[^\``\n])*`/g,                  css: 'string' },            // `` quoted strings
            { regex: /echo.*$/gmi,                                  css: 'string' },            // echo strings
            { regex: /%\*|%%?~?[fdpnxsatz]*[0-9a-z]\b/gmi,          css: 'variable' },          // %%ref or %1
            { regex: new RegExp(this.getKeywords(keywords), 'gmi'),  css: 'keyword' }           // keywords
            ];
    
        this.forHtmlScript(r.scriptScriptTags);
    };

    Brush.prototype = new SyntaxHighlighter.Highlighter();
    Brush.aliases   = ['vbs'];

    SyntaxHighlighter.brushes.VBScript = Brush;

    // CommonJS
    typeof (exports) !== 'undefined' ? exports.Brush = Brush : null;
})();
