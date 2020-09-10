<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="fabrica">
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
                <h1 style="font-family:Helvetica;font-size:45px;text-align:center" align="center">Factory</h1>
                <xsl:if test="count(producao/*) &gt; 0">
                    <xsl:apply-templates select="producao"/>
                </xsl:if>
                <xsl:if test="count(/) > 0"><br/><hr/><br/></xsl:if>
                <xsl:if test="count(chaoDeFabrica/*) &gt; 0">
                    <xsl:apply-templates select="chaoDeFabrica"/>
                </xsl:if>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="chaoDeFabrica">
        <h1 style="font-family:Helvetica;font-size:32px;text-align:center" align="center">Factory Floor</h1>
        <h3 style="font-family:Helvetica;text-align:center" align="center">Production Lines</h3>

        <table align="center" cellpadding="2" cellspacing="2" border="0">
            <tr bgcolor="#ffffff">

                <th style="font-family:Helvetica;font-size:16px;text-align:center">Internal Code</th>
                <th style="font-family:Helvetica;font-size:16px;text-align:center">Description</th>
            </tr>
            <xsl:apply-templates select="ProductionLines/ProductionLine">
                <xsl:sort select="@internalCode"/>
            </xsl:apply-templates>

        </table>
        <h2/>
        <table align="center" border="1">
            <tr bgcolor="#ffffff">
                <th style="text-align:center">Production Line Internal Code</th>
                <th style="text-align:center">Serial Number</th>
                <th style="text-align:center">Identification Number</th>
                <th style="text-align:center">Manufacturer</th>
                <th style="text-align:center">Model</th>
                <th style="text-align:center">Operation</th>
                <th style="text-align:center">Installation Date</th>
            </tr>
            <xsl:apply-templates select="ProductionLines/ProductionLine/Machines/Machine"/>
        </table>
        <h2/>
        <h3 style="font-family:Helvetica;text-align:center" align="center">Deposits</h3>
        <table align="center" cellpadding="2" cellspacing="2" border="0">
            <tr bgcolor="#ffffff">

                <th style="font-family:Helvetica;font-size:16px;text-align:center">Internal Code</th>
                <th style="font-family:Helvetica;font-size:16px;text-align:center">Description</th>
            </tr>
            <xsl:apply-templates select="Deposits/Deposit">
                <xsl:sort select="@internalCode"/>
            </xsl:apply-templates>
        </table>
    </xsl:template>

    <xsl:template match="Deposit">
        <tr>
            <td style="text-align:center">
                <xsl:value-of select="@internalCode"/>
            </td>

            <td style="text-align:center">
                <xsl:value-of select="desc"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="ProductionLine">
        <tr>
            <td style="text-align:center">
                <xsl:value-of select="@internalCode"/>
            </td>

            <td style="text-align:center">
                <xsl:value-of select="desc"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="ProductionLine/Machines/Machine">
        <tr>
            <td style="text-align:center">
                <xsl:value-of select="../../@internalCode"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="@serialNumber"/>
            </td>

            <td style="text-align:center">
                <xsl:value-of select="identificationNumber"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="manufacturer"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="model"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="operation"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="installationDate"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="producao">
        <h1 style="font-family:Helvetica;font-size:32px;text-align:center" align="center">Production</h1>
        <xsl:apply-templates select="Products"/>
        <xsl:apply-templates select="RawMaterials"/>
        <xsl:apply-templates select="RawMaterialCategorys"/>
        <xsl:apply-templates select="ProductionOrders"/>
    </xsl:template>

    <xsl:template match="Products">
        <h3 style="font-family:Helvetica;text-align:center" align="center">Products</h3>
        <table align="center" cellpadding="2" cellspacing="2" border="0">
            <tr bgcolor="#ffffff">

                <th style="font-family:Helvetica;font-size:16px;text-align:center">Fabrication Code</th>
                <th style="font-family:Helvetica;font-size:16px;text-align:center">Comercial Code</th>
                <th style="font-family:Helvetica;font-size:16px;text-align:center">Brief Description</th>
                <th style="font-family:Helvetica;font-size:16px;text-align:center">Full Description</th>
                <th style="font-family:Helvetica;font-size:16px;text-align:center">Product Category</th>
                <th style="font-family:Helvetica;font-size:16px;text-align:center">Unity</th>
            </tr>
            <xsl:apply-templates select="Product">
                <xsl:sort select="@fabricationCode"/>
            </xsl:apply-templates>
        </table>
        <h2/>
        <table align="center" border="1">
            <tr bgcolor="#ffffff">
                <th style="text-align:center">Product Fabrication Code</th>
                <th style="text-align:center">Quantity</th>
                <th style="text-align:center">Unity</th>
                <th style="text-align:center">Raw Material Code</th>
                <th style="text-align:center">Product Code</th>
            </tr>
            <xsl:apply-templates select="Product/ProductSheet"/>
        </table>
    </xsl:template>

    <xsl:template match="Product">
        <tr>
            <td style="text-align:center">
                <xsl:value-of select="@fabricationCode"/>
            </td>

            <td style="text-align:center">
                <xsl:value-of select="comercialCode"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="briefDescription"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="fullDescription"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="productCategory"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="unit"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="Product/ProductSheet">
        <tr>
            <td style="text-align:center">
                <xsl:value-of select="../@fabricationCode"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="Quantity/valor"/>
            </td>

            <td style="text-align:center">
                <xsl:value-of select="Quantity/unity"/>
            </td>

            <td style="text-align:center">
                <xsl:value-of select="ProductionSheetLine/RawMaterial/@code"/>
            </td>

            <td style="text-align:center">
                <xsl:value-of select="ProductionSheetLine/Product/@fabricationCode"/>
            </td>

        </tr>
    </xsl:template>

    <xsl:template match="RawMaterials">
        <h3 style="font-family:Helvetica;text-align:center" align="center">Raw Materials</h3>
        <table align="center" cellpadding="2" cellspacing="2" border="0">
            <tr bgcolor="#ffffff">

                <th style="font-family:Helvetica;font-size:16px;text-align:center">Raw Material Code</th>
                <th style="font-family:Helvetica;font-size:16px;text-align:center">Description</th>
                <th style="font-family:Helvetica;font-size:16px;text-align:center">Raw Material Category ID</th>
                <th style="font-family:Helvetica;font-size:16px;text-align:center">Raw Material Category Description
                </th>
            </tr>
            <xsl:apply-templates select="RawMaterial">
                <xsl:sort select="@code"/>
            </xsl:apply-templates>
        </table>
    </xsl:template>

    <xsl:template match="RawMaterial">
        <tr>
            <td style="text-align:center">
                <xsl:value-of select="@code"/>
            </td>

            <td style="text-align:center">
                <xsl:value-of select="description"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="RawMaterialCategory/@categoryID"/>
            </td>
            <td style="text-align:center">
                <xsl:value-of select="RawMaterialCategory/briefDescription"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="RawMaterialCategorys">

        <h3 style="font-family:Helvetica;text-align:center" align="center">Raw Material Categories</h3>
        <table align="center" cellpadding="2" cellspacing="2" border="0">
            <tr bgcolor="#ffffff">

                <th style="font-family:Helvetica;font-size:16px;text-align:center">Raw Material Category Code</th>
                <th style="font-family:Helvetica;font-size:16px;text-align:center">Raw Material Category Description
                </th>
            </tr>
            <xsl:apply-templates select="RawMaterialCategory">
                <xsl:sort select="@categoryID"/>
            </xsl:apply-templates>
        </table>
    </xsl:template>

    <xsl:template match="RawMaterialCategory">
        <tr>
            <td style="text-align:center">
                <xsl:value-of select="@categoryID"/>
            </td>

            <td style="text-align:center">
                <xsl:value-of select="briefDescription"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="ProductionOrders">
        <h1>Production Order List</h1>
        <table align="center" cellpadding="2" cellspacing="2" border="0">
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
            <h2>Production Order
                <xsl:value-of select="../@POCode"/> Report
            </h2>
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
        <table align="center" cellpadding="2" cellspacing="2" border="0">
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
        <table align="center" cellpadding="2" cellspacing="2" border="0">
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
        <table align="center" cellpadding="2" cellspacing="2" border="0">
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
        <table align="center" cellpadding="2" cellspacing="2" border="0">
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
        <table align="center" cellpadding="2" cellspacing="2" border="0">
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
        <xsl:if test="not (position() = last())">
            <br></br>
        </xsl:if>
    </xsl:template>

    <xsl:template match="Quantity">
        <xsl:value-of select="valor"/>
        <xsl:text> (</xsl:text>
        <xsl:value-of select="unity"/>
        <xsl:text>)</xsl:text>
    </xsl:template>

</xsl:stylesheet>



