
package nl.kvk.schemas.schemas.hrip.catalogus._2015._02;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RechtspersoonInOprichtingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RechtspersoonInOprichtingType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.kvk.nl/schemas/hrip/catalogus/2015/02}NietNatuurlijkPersoonType">
 *       &lt;sequence>
 *         &lt;element name="doelRechtsvorm" type="{http://schemas.kvk.nl/schemas/hrip/catalogus/2015/02}EnumeratieType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RechtspersoonInOprichtingType", propOrder = {
    "doelRechtsvorm"
})
public class RechtspersoonInOprichtingType
    extends NietNatuurlijkPersoonType
{

    protected EnumeratieType doelRechtsvorm;

    /**
     * Gets the value of the doelRechtsvorm property.
     * 
     * @return
     *     possible object is
     *     {@link EnumeratieType }
     *     
     */
    public EnumeratieType getDoelRechtsvorm() {
        return doelRechtsvorm;
    }

    /**
     * Sets the value of the doelRechtsvorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumeratieType }
     *     
     */
    public void setDoelRechtsvorm(EnumeratieType value) {
        this.doelRechtsvorm = value;
    }

}
