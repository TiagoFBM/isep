<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/fabrica/producao/ProductionOrders">
        <html>
            <head>
                <style>
                    table {
                    font-family: arial, sans-serif;
                    border-collapse: collapse;
                    width: 100%;
                    }

                    td {
                    border: 1px solid #dddddd;
                    text-align:center;
                    padding: 8px;
                    }

                    th {
                    border: 1px solid #dddddd;
                    text-align:center;
                    padding: 8px;
                    font-family:Helvetica;
                    font-size:16px;
                    }

                    tr:nth-child(even) {
                    background-color: #dddddd;
                    }

                    h1 {
                    font-family:Helvetica;
                    font-size:32px;
                    text-align:center;
                    }

                    h2 {
                    text-align:center;
                    }
                </style>
            </head>
            <body>
                <h1>Production Order List</h1>
                <table align="center" cellpadding="2" cellspacing="2" border="0" >
                    <tr bgcolor="#ffffff">
                        <th>Production Order Identifier</th>
                        <th>Emission Date</th>
                        <th>Prevision Date</th>
                        <th>Product Fabrication Code</th>
                        <th>Amount (unit)</th>
                        <th>Standard Unit</th>
                        <th>Requests</th>
                        <th>State</th>
                        <th>Production Line Internal Code</th>

                    </tr>
                    <xsl:apply-templates select="ProductionOrder"/>
                </table>
                <br/>
                <xsl:apply-templates select="ProductionOrder/ProductionOrderReport"/>
                <br/>

            </body>
        </html>
    </xsl:template>

    <xsl:template match="ProductionOrder">
        <tr>
            <td>
                <xsl:value-of select="@POCode"/>
            </td>
            <td>
                <xsl:value-of select="emissionDate"/>
            </td>
            <td>
                <xsl:value-of select="previsionDate"/>
            </td>
            <td>
                <xsl:value-of select="Product/@fabricationCode"/>
            </td>
            <td>
                <xsl:apply-templates select="Quantity"/>
            </td>
            <td>
                <xsl:value-of select="unit"/>
            </td>
            <td>
                <xsl:apply-templates select="Requests/request"/>
            </td>
            <td>
                <xsl:value-of select="state"/>
            </td>
            <td>
                <xsl:value-of select="ProductionLine/@internalCode"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="ProductionOrder/ProductionOrderReport">
        <div id="{../@POCode}Report">
            <h2>Production Order <xsl:value-of select="../@POCode"/> Report</h2>
            <br/>
            <xsl:apply-templates select="Consumptions"/>
            <br/>
            <xsl:apply-templates select="Chargebacks"/>
            <br/>
            <xsl:apply-templates select="Productions"/>
            <br/>
            <xsl:apply-templates select="ProductionDeliverys"/>
            <br/>
            <xsl:apply-templates select="MachineTimes"/>
        </div>
    </xsl:template>

    <xsl:template match="Consumptions">
        <h3>Consumptions</h3>
        <table align="center" cellpadding="2" cellspacing="2" border="0" >
            <tr bgcolor="#ffffff">
                <th>Product Fabrication Code</th>
                <th>Raw Material Code</th>
                <th>Amount (unit)</th>
                <th>Deposit identifier</th>
                <th>Movement Date</th>
            </tr>
            <xsl:apply-templates select="consumption"/>
        </table>
    </xsl:template>

    <xsl:template match="consumption">
        <tr>
            <td>
                <xsl:if test="not(Product)">-----</xsl:if>
                <xsl:value-of select="Product/@fabricationCode"/>
            </td>
            <td>
                <xsl:if test="not(RawMaterial)">-----</xsl:if>
                <xsl:value-of select="RawMaterial/@code"/>
            </td>
            <td>
                <xsl:apply-templates select="Quantity"/>
            </td>
            <td>
                <xsl:value-of select="Deposit/@internalCode"/>
            </td>
            <td>
                <xsl:value-of select="movementDate"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="Chargebacks">
        <h3>Chargebacks</h3>
        <table align="center" cellpadding="2" cellspacing="2" border="0" >
            <tr bgcolor="#ffffff">
                <th>Product Fabrication Code</th>
                <th>Raw Material Code</th>
                <th>Amount (unit)</th>
                <th>Deposit identifier</th>
            </tr>
            <xsl:apply-templates select="chargeback"/>
        </table>
    </xsl:template>

    <xsl:template match="chargeback">
        <tr>
            <td>
                <xsl:if test="not(Product)">-----</xsl:if>
                <xsl:value-of select="Product/@fabricationCode"/>
            </td>
            <td>
                <xsl:if test="not(RawMaterial)">-----</xsl:if>
                <xsl:value-of select="RawMaterial/@code"/>
            </td>
            <td>
                <xsl:apply-templates select="Quantity"/>
            </td>
            <td>
                <xsl:value-of select="Deposit/@internalCode"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="Productions">
        <h3>Productions</h3>
        <table align="center" cellpadding="2" cellspacing="2" border="0" >
            <tr bgcolor="#ffffff">
                <th>Product Fabrication Code</th>
                <th>Amount (unit)</th>
                <th>Allotment</th>
            </tr>
            <xsl:apply-templates select="production"/>
        </table>
    </xsl:template>

    <xsl:template match="production">
        <tr>
            <td>
                <xsl:value-of select="Product/@fabricationCode"/>
            </td>
            <td>
                <xsl:apply-templates select="Quantity"/>
            </td>
            <td>
                <xsl:if test="not(allotment)">-----</xsl:if>
                <xsl:value-of select="allotment"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="ProductionDeliverys">
        <h3>ProductionDeliverys</h3>
        <table align="center" cellpadding="2" cellspacing="2" border="0" >
            <tr bgcolor="#ffffff">
                <th>Product Fabrication Code</th>
                <th>Amount (unit)</th>
                <th>Deposit identifier</th>
                <th>Allotment</th>
            </tr>
            <xsl:apply-templates select="productionDelivery"/>
        </table>
    </xsl:template>

    <xsl:template match="productionDelivery">
        <tr>
            <td>
                <xsl:value-of select="Product/@fabricationCode"/>
            </td>
            <td>
                <xsl:apply-templates select="Quantity"/>
            </td>
            <td>
                <xsl:value-of select="Deposit/@internalCode"/>
            </td>
            <td>
                <xsl:if test="not(allotment)">-----</xsl:if>
                <xsl:value-of select="allotment"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="MachineTimes">
        <h3>MachineTimes</h3>
        <table align="center" cellpadding="2" cellspacing="2" border="0" >
            <tr bgcolor="#ffffff">
                <th>Machine Serial Number</th>
                <th>Date and Time</th>
                <th>New Machine State</th>
            </tr>
            <xsl:apply-templates select="machineTime"/>
        </table>
    </xsl:template>

    <xsl:template match="machineTime">
        <tr>
            <td>
                <xsl:value-of select="Machine/@serialNumber"/>
            </td>
            <td>
                <xsl:value-of select="date"/>
            </td>
            <td>
                <xsl:value-of select="newStatus"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="Requests/request">
        <xsl:value-of select="code"/>
        <xsl:if test="not (position() = last())"><br></br></xsl:if>
    </xsl:template>

    <xsl:template match="Quantity">
        <xsl:value-of select="valor"/>
        <xsl:text> (</xsl:text>
        <xsl:value-of select="unity"/>
        <xsl:text>)</xsl:text>
    </xsl:template>
</xsl:stylesheet>