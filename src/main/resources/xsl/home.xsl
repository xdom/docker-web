<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
    <xsl:template match="page">
        <html>
            <body>
                <div class="wrapper" style="text-align:center;">
                    <h2>
                        <xsl:text>Docker Task Runner</xsl:text>
                    </h2>
                    <p>OS:
                        <xsl:apply-templates select="status"/>
                    </p>
                    <p>Containers:
                        <xsl:apply-templates select="containers"/>
                    </p>
                    <form method="get" action="/build">
                        <input type="text" name="cmd"/>
                        <input type="submit"/>
                    </form>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
