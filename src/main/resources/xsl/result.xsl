<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
    <xsl:template match="page">
        <html>
            <body>
                <div class="wrapper" style="text-align:center;">
                    <p>Result:
                        <xsl:apply-templates select="result"/>
                    </p>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
