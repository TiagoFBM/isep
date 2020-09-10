<?xml version="1.0" encoding="UTF-8" ?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:ns="http://www.dei.isep.ipp.pt/lprog">
	<xsl:output method="html" />

	<xsl:template match="/ns:relatório">
		<html>
            <head>
            <meta charset="UTF-8"/>
            <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
            <title>Relatório LPROG</title>
            </head>

            <body>
				<font face="Helvetica">
					<xsl:apply-templates select="//ns:logotipoDEI" />
					<p></p>
					<font size="7" color="#ff7400">
						<b>
							<xsl:value-of select="ns:páginaRosto/ns:tema" />
						</b>
					</font>
					<p></p>
					<font size="5">
						<b>
							<xsl:value-of select="ns:páginaRosto/ns:disciplina/ns:designação" />
							-
							<xsl:value-of select="ns:páginaRosto/ns:disciplina/ns:sigla" />
							<xsl:text>
								<!--  -->
							</xsl:text>
							<xsl:value-of select="ns:páginaRosto/ns:disciplina/ns:anoCurricular" />
							º Ano
						</b>
						<br></br>
					</font>
					<font size="4">
						<font color="#636363">Turma: </font>
						<xsl:value-of select="ns:páginaRosto/ns:turma" />
					</font>
					<p></p>
					<font size="4">
						<font color="#636363">Autores:</font>
						<br></br>
						<xsl:apply-templates select="ns:páginaRosto/ns:autor" />
					</font>
					<p></p>
					<font size="4">
						<xsl:apply-templates select="ns:páginaRosto/ns:professor" />
					</font>
					<xsl:apply-templates select="//ns:data" />
					<p></p>
					<hr />

					<p></p>
					<font size="5">
						<b>Índice</b>
					</font>
					<p></p>
					<font size="4">
						<li>
							<a href="#SEC001">Introdução</a>
						</li>
						<li>
							<a href="#SEC002">Análise</a>
						</li>
						<li>
							<a href="#SEC003">Linguagem</a>
						</li>
						<li>
							<a href="#SEC004">Transformações</a>
						</li>
						<li>
							<a href="#SEC005">Conclusão</a>
						</li>
						<li>
							<a href="#ANX001">Anexos</a>
						</li>
						<li>
							<a href="#SEC006">Referências</a>
						</li>
					</font>
					<p></p>
					<hr />
					<font size="4">
						<xsl:apply-templates select="//ns:introdução" />
					</font>
					<hr />
					<font size="4">
						<xsl:apply-templates select="//ns:análise" />
					</font>
					<hr />
					<font size="4">
						<xsl:apply-templates select="//ns:linguagem" />
					</font>
					<hr />
					<font size="4">
						<xsl:apply-templates select="//ns:transformações" />
					</font>
					<hr />
					<font size="4">
						<xsl:apply-templates select="//ns:conclusão" />
					</font>
					<hr />
					<font size="4">
						<xsl:apply-templates select="//ns:anexos" />
					</font>
					<hr />
					<font size="4">
						<xsl:apply-templates select="//ns:referências" />
					</font>
				</font>
			</body>
		</html>
	</xsl:template>
	<!--=========================================================================Templates========================================================================= -->
	<!-- Template para logotipo do dei -->
	<xsl:template match="ns:logotipoDEI">
		<xsl:element name="img">
			<xsl:attribute name="src">
				<xsl:value-of select="." />
			</xsl:attribute>
			<xsl:attribute name="align">right</xsl:attribute>
		</xsl:element>
	</xsl:template>

	<!-- Data de Apresentação-->
	<xsl:template match="ns:data">
		<xsl:element name="p">
			<xsl:attribute name="align">right</xsl:attribute>
			Data:
			<xsl:value-of select="." />
		</xsl:element>
	</xsl:template>

	<!-- Professores -->
	<xsl:template match="ns:páginaRosto/ns:professor">
		<font color="#636363">Professor(a): </font>
		<xsl:value-of select="@sigla" />
		- Tipo de Aula:
		<xsl:value-of select="@tipoAula" />
		<br></br>
	</xsl:template>

	<!-- Autores -->
	<xsl:template match="ns:páginaRosto/ns:autor">
		<xsl:value-of select="ns:nome" />
		-
		<xsl:value-of select="ns:número" />
		-
		<xsl:value-of select="ns:mail" />
		<br></br>
	</xsl:template>

	<!-- Introducao -->
	<xsl:template match="ns:introdução">
		<h2 id="SEC001">
			<xsl:value-of select="@tituloSecção" />
		</h2>
		<p align="justify">
			<xsl:apply-templates select="*" />
		</p>
	</xsl:template>

	<!-- Análise -->
	<xsl:template match="ns:análise">
		<h2 id="SEC002">
			<xsl:value-of select="@tituloSecção" />
		</h2>
		<p align="justify">
			<xsl:apply-templates select="*" />
		</p>
	</xsl:template>

	<!-- Linguagem -->
	<xsl:template match="ns:linguagem">
		<h2 id="SEC003">
			<xsl:value-of select="@tituloSecção" />
		</h2>
		<p align="justify">
			<xsl:apply-templates select="*" />
		</p>
	</xsl:template>

<!-- Transformacoes -->
<xsl:template match="ns:transformações">
	<h2 id="SEC004">
		<xsl:value-of select="@tituloSecção" />
	</h2>
	<p align="justify">
		<xsl:apply-templates select="*" />
	</p>
</xsl:template>

<!-- Conclusao -->
<xsl:template match="ns:conclusão">
	<h2 id="SEC005">
		<xsl:value-of select="@tituloSecção" />
	</h2>
	<p align="justify">
		<xsl:apply-templates select="*" />
	</p>
</xsl:template>

<!-- Anexos -->
<xsl:template match="ns:anexos">
	<h2 id="ANX001">
		<xsl:value-of select="@tituloSecção" />
	</h2>
	<p align="justify">
		<xsl:apply-templates select="*" />
	</p>
</xsl:template>

<!-- Referencias WEB -->
<xsl:template match="ns:referências">
	<h2 id="SEC006">
		<xsl:value-of select="@tituloSecção" />
	</h2>
	<xsl:apply-templates select="ns:refWeb" />
</xsl:template>
<xsl:template match="ns:refWeb">
	<xsl:element name="a">
		<xsl:attribute name="href">
			<xsl:value-of select="ns:URL" />
		</xsl:attribute>
		<xsl:attribute name="target"></xsl:attribute>
		<xsl:value-of select="ns:URL" />
	</xsl:element>
	<br />
	<xsl:value-of select="ns:descrição" />
	(
	<xsl:value-of select="ns:consultadoEm" />
	)
	<br />
	<br />
</xsl:template>

	<!-- Subsecção-->
	<xsl:template match="ns:subsecção">
		<h3>
			<xsl:value-of select="." />
		</h3>
	</xsl:template>

	<!-- Bloco de Código-->
	<xsl:template match="ns:bloco">
		<p></p>
		<xsl:element name="details">
			<xsl:element name="summary">Código Exemplo:</xsl:element>
			<xsl:element name="pre">
				<xsl:element name="code">
					<xsl:value-of select="." />
				</xsl:element>
			</xsl:element>
		</xsl:element>
		<p></p>
	</xsl:template>
	
	<!-- Figura -->
	<xsl:template match="ns:figura">
		<xsl:element name="img">
            <xsl:attribute name="src"><xsl:value-of select="./@src"/></xsl:attribute>
		</xsl:element>
		<p align="justify">
		<font size ="2" face = "Helvetica">
		<xsl:element name="caption"><xsl:value-of select="./@descrição"/></xsl:element>
		</font>
	</p>
	</xsl:template>

	<!-- Lista de Items -->
	<xsl:template match="ns:item">
		<xsl:element name="li">
			<xsl:value-of select="."/>
		</xsl:element>
	</xsl:template>

	<!-- Paragrafo -->
	<xsl:template match="ns:parágrafo">
		<xsl:apply-templates />
		<br />
	</xsl:template>

	<!--sublinhado-->
	<xsl:template match="ns:sublinhado">
		<xsl:element name="u">
			<xsl:value-of select="."/>
		</xsl:element>
	</xsl:template>

	<!--negrito-->
	<xsl:template match="ns:negrito">
		<xsl:element name="b">
			<xsl:value-of select="."/>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>