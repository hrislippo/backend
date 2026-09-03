package lippo.hris.system.recruitment.enumeration;

import java.util.List;

public enum UniversityMDP {
    UPH(List.of("UPH", "PELITA HARAPAN")),
    BINUS(List.of("BINUS", "BINA NUSANTARA")),
    UNTAR(List.of("UNTAR", "TARUMANAGARA")),
    ITB(List.of("ITB", "INSTITUT TEKNOLOGI BANDUNG")),
    UI(List.of("UNIVERSITAS INDONESIA")),
    UGM(List.of("UGM", "GAJAH MADA", "GADJAH MADA")),
    UNPAR(List.of("UNPAR", "PARAHYANGAN")),
    UMN(List.of("UMN", "MULTIMEDIA NUSANTARA")),
    ATMAJAYA(List.of("ATMAJAYA", "ATMA JAYA")),
    BRAWIJAYA(List.of("BRAWIJAYA")),
    MONASH(List.of("MONASH")),
    MELBOURNE(List.of("MELBOURNE")),
    SIM(List.of("SINGAPORE INSTITUTE OF MANAGEMENT")),
    AIRLANGGA(List.of("AIRLANGGA")),
    IPB(List.of("IPB", "PERTANIAN BOGOR")),
    UNPAD(List.of("UNPAD", "PADJADJARAN", "PAJAJARAN", "PADJAJARAN")),
    SEPULUHNOVEMBER(List.of("SEPULUH NOVEMBER")),
    DIPONEGORO(List.of("DIPONEGORO")),
    SEBELASMARET(List.of("SEBELAS MARET")),
    HASANUDDIN(List.of("UNIVERSITAS HASANUDDIN")),
    TELKOM(List.of("TELKOM")),
    SUMATERAUTARA(List.of("SUMATERA UTARA")),
    PENDIDIKANINDONESIA(List.of("PENDIDIKAN INDONESIA")),
    ANDALAS(List.of("ANDALAS")),
    NEGERIYOGYAKARTA(List.of("UNIVERSITAS NEGERI YOGYAKARTA")),
    UDAYANA(List.of("UDAYANA")),
    PETRA(List.of("PETRA")),
    NEGERIMALANG(List.of("UNIVERSITAS NEGERI MALANG")),
    SYIAHKUALA(List.of("SYIAH KUALA")),
    TEKNOLOGINASIONALBANDUNG(List.of("TEKNOLOGI NASIONAL BANDUNG")),
    SURABAYA(List.of("UNIVERSITAS SURABAYA")),
    LAMPUNG(List.of("UNIVERSITAS LAMPUNG")),
    NEGERIPADANG(List.of("UNIVERSITAS NEGERI PADANG")),
    TRISAKTI(List.of("TRISAKTI")),
    MATARAM(List.of("UNIVERSITAS MATARAM")),
    CIPUTRA(List.of("CIPUTRA")),
    NEGERISEMARANG(List.of("UNIVERSITAS NEGERI SEMARANG")),
    JEMBER(List.of("UNIVERSITAS JEMBER")),
    NEGERIJAKARTA(List.of("UNIVERSITAS NEGERI JAKARTA")),
    SOEDIRMAN(List.of("SOEDIRMAN")),
    MARANATHA(List.of("MARANATHA")),
    SRIWIJAYA(List.of("UNIVERSITAS SRIWIJAYA")),
    SATYAWACANA(List.of("SATYA WACANA")),
    NEGERISURABAYA(List.of("UNIVERSITAS NEGERI SURABAYA")),
    MERCUBUANA(List.of("MERCU BUANA")),
    WIDYAMANDALA(List.of("WIDYA MANDALA SURABAYA")),
    NUSAPUTRA(List.of("NUSA PUTRA")),
    PRESIDENT(List.of("PRESIDEN")),
    SANATADHARMA(List.of("SANATA DHARMA")),
    NEGERIMAKASSAR(List.of("UNIVERSITAS NEGERI MAKASSAR")),
    SAMRATULANGI(List.of("SAM RATULANGI")),
    BENGKULU(List.of("UNIVERSITAS BENGKULU")),
    SOEGIJAPRANATA(List.of("SOEGIJAPRANATA")),
    NEGERIMEDAN(List.of("UNIVERSITAS NEGERI MEDAN")),
    MEDANAREA(List.of("MEDAN AREA")),
    DIANNUSWANTORO(List.of("DIAN NUSWANTORO")),
    KOMPUTERINDONESIA(List.of("KOMPUTER INDONESIA")),
    LAMBUNGMANGKURAT(List.of("LAMBUNG MANGKURAT")),
    MULAWARMAN(List.of("MULAWARMAN")),
    VETERANJAKARTA(List.of("VETERAN JAKARTA")),
    GANESHA(List.of("PENDIDIKAN GANESHA")),
    RIAU(List.of("UNIVERSITAS RIAU")),
    TANJUNGPURA(List.of("UNIVERSITAS TANJUNGPURA")),
    BAKRIE(List.of("BAKRIE")),
    ESAUNGGUL(List.of("ESA UNGGUL")),
    MERDEKAMALANG(List.of("MERDEKA MALANG")),
    TADULAKO(List.of("TADULAKO")),
    GUNADARMA(List.of("GUNADARMA")),
    JAMBI(List.of("UNIVERSITAS JAMBI")),
    PANCASILA(List.of("UNIVERSITAS PANCASILA")),
    VETERANJAWATIMUR(List.of("VETERAN JAWA TIMUR")),
    PRIMAINDONESIA(List.of("PRIMA INDONESIA")),
    WARMADEWA(List.of("WARMADEWA")),
    AGUSTUS1945SURABAYA(List.of("AGUSTUS 1945 SURABAYA")),
    PANCABUDI(List.of("PANCA BUDI")),
    PENDIDIKANNASIONAL(List.of("PENDIDIKAN NASIONAL")),
    WIDYAGAMA(List.of("WIDYAGAMA")),
    WIDYATAMA(List.of("WIDYATAMA"));

    private final List<String> values;

    UniversityMDP(List<String> values) {
        this.values = values;
    }

    public boolean contains(String value) {
        if (value == null) {
            return false;
        }

        return values.stream()
                .anyMatch(label -> value.toUpperCase().contains(label));
    }

    public static UniversityMDP find(String value) {
        if (value == null) {
            return null;
        }

        for (UniversityMDP field : values()) {
            if (field.contains(value)) {
                return field;
            }
        }

        return null;
    }
}
