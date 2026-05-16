<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Растения оранжереи</title>
                <style>
                    table { border-collapse: collapse; width: 100%; }
                    th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
                    th { background-color: #ccc; }
                </style>
            </head>
            <body>
                <h1>Растения, отсортированные по предпочитаемой температуре (по возрастанию)</h1>
                <table>
                    <thead>
                        <tr>
                            <th>Название</th>
                            <th>Почва</th>
                            <th>Происхождение</th>
                            <th>Внешние параметры</th>
                            <th>Температура</th>
                            <th>Свет</th>
                            <th>Полив (мл/нед)</th>
                            <th>Размножение</th>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:for-each select="Flower/Plant">
                            <xsl:sort select="GrowingTips/Temperature" data-type="number" order="ascending"/>
                            <tr>
                                <td><xsl:value-of select="Name"/></td>
                                <td><xsl:value-of select="Soil"/></td>
                                <td><xsl:value-of select="Origin"/></td>
                                <td>
                                    стебель: <xsl:value-of select="VisualParameters/StemColor"/>,
                                    листья: <xsl:value-of select="VisualParameters/LeafColor"/>,
                                    размер: <xsl:value-of select="VisualParameters/AverageSize"/>
                                </td>
                                <td><xsl:value-of select="GrowingTips/Temperature"/></td>
                                <td><xsl:value-of select="GrowingTips/Light"/></td>
                                <td><xsl:value-of select="GrowingTips/Watering"/></td>
                                <td><xsl:value-of select="Multiplying"/></td>
                            </tr>
                        </xsl:for-each>
                    </tbody>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>