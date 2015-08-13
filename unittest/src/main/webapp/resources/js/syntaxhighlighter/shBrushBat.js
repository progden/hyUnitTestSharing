
;(function()
{
	// CommonJS
	typeof(require) != 'undefined' ? SyntaxHighlighter = require('shCore').SyntaxHighlighter : null;

	function Brush()
	{
		var keywords =	'CON DEFINED DO ENABLEDELAYEDEXPANSION ENABLEEXTENSIONS ENDLOCAL FOR GOTO CALL IF IN ELSE NOT NUL REM SET SETLOCAL';

    	var commands = 'ASSOC AT ATTRIB BREAK CACLS CD CHCP CHDIR CHKDSK CHKNTFS CLS CMD COLOR COMP COMPACT CONVERT COPY DATE '
                 + 'DEL DIR DISKCOMP DISKCOPY DOSKEY ECHO ERASE EXIT FC FIND FINDSTR FORMAT FTYPE GRAFTABL '
                 + 'HELP LABEL MD MKDIR MODE MORE MOVE PATH PAUSE POPD PRINT PROMPT PUSHD RD RECOVER REN RENAME REPLACE '
                 + 'RMDIR SHIFT SORT START SUBST TIME TITLE TREE TYPE VER VERIFY VOL XCOPY';

    	var variables = 'ALLUSERSPROFILE APPDATA CommonProgramFiles COMPUTERNAME ComSpec DATE FP_NO_HOST_CHECK HOMEDRIVE '
                  + 'HOMEPATH LOGONSERVER NUMBER_OF_PROCESSORS OS Path PATHEXT PROCESSOR_ARCHITECTURE PROCESSOR_IDENTIFIER '
                  + 'PROCESSOR_LEVEL PROCESSOR_REVISION ProgramFiles PROGS PROMPT SANDBOX_DISK SANDBOX_PATH SESSIONNAME '
                  + 'SystemDrive SystemRoot TEMP TIME TMP USERDNSDOMAIN USERDOMAIN USERNAME USERPROFILE windir';
		var r = SyntaxHighlighter.regexLib;
		
		this.regexList = [
			{ regex: r.multiLineDoubleQuotedString,					css: 'string' },			// double quoted strings
			{ regex: r.multiLineSingleQuotedString,					css: 'string' },			// single quoted strings
			{ regex: /`(?:\.|(\\\`)|[^\``\n])*`/g,					css: 'string' },			// `` quoted strings
			{ regex: /echo.*$/gmi,									css: 'string' },			// echo strings
			{ regex: /^:.+$/gmi, 									css: 'color3' },			// :Label
			{ regex: /(%|!)\w+\1/gmi, 								css: 'variable' },			// %Variable% or !Variable
			{ regex: /%\*|%%?~?[fdpnxsatz]*[0-9a-z]\b/gmi, 			css: 'variable' },			// %%ref or %1
			{ regex: new RegExp(this.getKeywords(keywords), 'gm'),	css: 'keyword' },			// keywords
			{ regex: new RegExp(this.getKeywords(variables), 'gm'),	css: 'keyword' },			// commands
			{ regex: new RegExp(this.getKeywords(commands), 'gm'),	css: 'variable' }			// commands
			];
	
		this.forHtmlScript(r.scriptScriptTags);
	};

	Brush.prototype	= new SyntaxHighlighter.Highlighter();
	Brush.aliases	= ['bat', 'cmd', 'batch'];

	SyntaxHighlighter.brushes.Cmd = Brush;

	// CommonJS
	typeof(exports) != 'undefined' ? exports.Brush = Brush : null;
})();
