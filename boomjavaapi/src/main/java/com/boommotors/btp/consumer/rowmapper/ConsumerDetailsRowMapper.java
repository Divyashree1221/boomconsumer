/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.consumer.rowmapper;

import com.boommotors.btp.consumer.dto.ConsumerDetailsDTO;
import com.boommotors.btp.util.DatabaseUtil;
import com.boommotors.btp.util.EncryptDecrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author divyashree
 */
public class ConsumerDetailsRowMapper implements RowMapper<ConsumerDetailsDTO> {

    EncryptDecrypt ed = new EncryptDecrypt();
    DatabaseUtil du = new DatabaseUtil();

    @Override
    public ConsumerDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ConsumerDetailsDTO data = new ConsumerDetailsDTO();

        data.setFirstname(rs.getString("firstname"));
        data.setLastname(rs.getString("lastname"));
        data.setMobileNumber(rs.getString("mobile_number"));
        data.setEmailId(rs.getString("email_id"));
//        data.setUserId(ed.encrypt(String.valueOf(rs.getLong("user_id"))));
//        data.setOrderSummaryId(ed.encrypt(String.valueOf(rs.getLong("order_summary_id"))));
//        data.setVariantId(ed.encrypt(String.valueOf(rs.getLong("variant_id"))));
//        data.setVariantName(rs.getString("variant_name"));
//        data.setColourName(rs.getString("colour_name"));
//        data.setFinanceEmi(rs.getDouble("finance_emi"));
//        data.setInsuranceType(rs.getString("insurance_type"));
//        data.setInsuranceAmount(rs.getDouble("insurance_amount"));
//        data.setWarrantyAmount(rs.getDouble("warranty_amount"));
//        data.setDeliveryType(rs.getString("delivery_type"));
//        data.setPincode(rs.getString("pincode"));
//        data.setCity(rs.getString("city"));
//        data.setState(rs.getString("state"));
//        data.setCountry(rs.getString("country"));
//        data.setIsOutOfCoverage(rs.getBoolean("is_out_of_coverage"));
//        data.setQuantity(rs.getInt("quantity"));
//        data.setRfpSent(rs.getBoolean("rfp_sent"));
//        data.setRfpSent(rs.getBoolean("rfp_sent"));
//        data.setTotalAmount(rs.getDouble("total_amount"));
//        data.setAdvanceAmount(rs.getDouble("advance_amount"));
//        data.setAmountPaid(rs.getDouble("amount_paid"));
//        data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
//        data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
//        data.setCreatedDate(rs.getTimestamp("created_date"));
//        data.setUpdatedDate(rs.getTimestamp("updated_date"));
//        data.setPackageName(rs.getString("package_name"));
//        data.setColourAmount(rs.getDouble("colour_amount"));
//        data.setExShowroom(rs.getDouble("ex_showroom"));
//        data.setPackageAmt(rs.getDouble("package_amt"));
//        data.setSubTotal(rs.getDouble("sub_total"));
//        data.setGstAmt(rs.getDouble("gst_amt"));
//        data.setGrossTotal(rs.getDouble("gross_total"));
//        data.setSubsidyAmt(rs.getDouble("subsidy_amt"));
//        data.setTenure(rs.getInt("tenure"));
//        data.setExchange(rs.getString("exchange"));

        boolean doesUserIdExists = du.isThere(rs, "user_id");
        if (doesUserIdExists) {
            if (!rs.wasNull()) {
                data.setUserId(ed.encrypt(String.valueOf(rs.getLong("user_id"))));
            } else {
                data.setUserId("");
            }
        } else {
            data.setUserId("");
        }

        boolean doesOrderSummaryIdExists = du.isThere(rs, "order_summary_id");
        if (doesOrderSummaryIdExists) {
            if (!rs.wasNull()) {
                data.setOrderSummaryId(ed.encrypt(String.valueOf(rs.getLong("order_summary_id"))));
            } else {
                data.setOrderSummaryId("");
            }
        } else {
            data.setOrderSummaryId("");
        }

        boolean doesVariantIdExists = du.isThere(rs, "variant_id");
        if (doesVariantIdExists) {
            if (!rs.wasNull()) {
                data.setVariantId(ed.encrypt(String.valueOf(rs.getLong("variant_id"))));
            } else {
                data.setVariantId("");
            }
        } else {
            data.setVariantId("");
        }

        boolean doesVariantNameExists = du.isThere(rs, "variant_name");
        if (doesVariantNameExists) {
            if (!rs.wasNull()) {
                data.setVariantName(rs.getString("variant_name"));
            } else {
                data.setVariantName("");
            }
        } else {
            data.setVariantName("");
        }

        boolean doesColourNameExists = du.isThere(rs, "colour_name");
        if (doesColourNameExists) {
            if (!rs.wasNull()) {
                data.setColourName(rs.getString("colour_name"));
            } else {
                data.setColourName("");
            }
        } else {
            data.setColourName("");
        }

        boolean doesFinanceEmiExists = du.isThere(rs, "finance_emi");
        if (doesFinanceEmiExists) {
            if (!rs.wasNull()) {
                data.setFinanceEmi(rs.getDouble("finance_emi"));
            } else {
                data.setFinanceEmi(0.0);
            }
        } else {
            data.setFinanceEmi(0.0);
        }

        boolean doesInsuranceTypeExists = du.isThere(rs, "insurance_type");
        if (doesInsuranceTypeExists) {
            if (!rs.wasNull()) {
                data.setInsuranceType(rs.getString("insurance_type"));
            } else {
                data.setInsuranceType("");
            }
        } else {
            data.setInsuranceType("");
        }

        boolean doesInsuranceAmtExists = du.isThere(rs, "insurance_amount");
        if (doesInsuranceAmtExists) {
            if (!rs.wasNull()) {
                data.setInsuranceAmount(rs.getDouble("insurance_amount"));
            } else {
                data.setInsuranceAmount(0.0);
            }
        } else {
            data.setInsuranceAmount(0.0);
        }

        boolean doesWarrantyAmtExists = du.isThere(rs, "warranty_amount");
        if (doesWarrantyAmtExists) {
            if (!rs.wasNull()) {
                data.setWarrantyAmount(rs.getDouble("warranty_amount"));
            } else {
                data.setWarrantyAmount(0.0);
            }
        } else {
            data.setWarrantyAmount(0.0);
        }

        boolean doesDeliveryTypeExists = du.isThere(rs, "delivery_type");
        if (doesDeliveryTypeExists) {
            if (!rs.wasNull()) {
                data.setDeliveryType(rs.getString("delivery_type"));
            } else {
                data.setDeliveryType("");
            }
        } else {
            data.setDeliveryType("");
        }

        boolean doesPincodeExists = du.isThere(rs, "pincode");
        if (doesPincodeExists) {
            if (!rs.wasNull()) {
                data.setPincode(rs.getString("pincode"));
            } else {
                data.setPincode("");
            }
        } else {
            data.setPincode("");
        }

        boolean doesCityExists = du.isThere(rs, "city");
        if (doesCityExists) {
            if (!rs.wasNull()) {
                data.setCity(rs.getString("city"));
            } else {
                data.setCity("");
            }
        } else {
            data.setCity("");
        }

        boolean doesStateExists = du.isThere(rs, "state");
        if (doesStateExists) {
            if (!rs.wasNull()) {
                data.setState(rs.getString("state"));
            } else {
                data.setState("");
            }
        } else {
            data.setState("");
        }

        boolean doesCountryExists = du.isThere(rs, "country");
        if (doesCountryExists) {
            if (!rs.wasNull()) {
                data.setCountry(rs.getString("country"));
            } else {
                data.setCountry("");
            }
        } else {
            data.setCountry("");
        }

        boolean doesIsOutOfCoverageExists = du.isThere(rs, "is_out_of_coverage");
        if (doesIsOutOfCoverageExists) {
            if (!rs.wasNull()) {
                data.setIsOutOfCoverage(rs.getBoolean("is_out_of_coverage"));
            } else {
                data.setIsOutOfCoverage(false);
            }
        } else {
            data.setIsOutOfCoverage(false);
        }

        boolean doesQuantityExists = du.isThere(rs, "quantity");
        if (doesQuantityExists) {
            if (!rs.wasNull()) {
                data.setQuantity(rs.getInt("quantity"));
            } else {
                data.setQuantity(1);
            }
        } else {
            data.setQuantity(1);
        }
        data.setRfpSent(rs.getBoolean("rfp_sent"));
        boolean doesRfpSentExists = du.isThere(rs, "is_out_of_coverage");
        if (doesRfpSentExists) {
            if (!rs.wasNull()) {
                data.setRfpSent(rs.getBoolean("rfp_sent"));
            } else {
                data.setRfpSent(false);
            }
        } else {
            data.setRfpSent(false);
        }
//        data.setRfpSent(rs.getBoolean("rfp_sent"));

        boolean doesTotalAmtExists = du.isThere(rs, "total_amount");
        if (doesTotalAmtExists) {
            if (!rs.wasNull()) {
                data.setTotalAmount(rs.getDouble("total_amount"));
            } else {
                data.setTotalAmount(0.0);
            }
        } else {
            data.setTotalAmount(0.0);
        }

        boolean doesAmountExists = du.isThere(rs, "advance_amount");
        if (doesAmountExists) {
            if (!rs.wasNull()) {
                data.setAdvanceAmount(rs.getDouble("advance_amount"));
            } else {
                data.setAdvanceAmount(0.0);
            }
        } else {
            data.setAdvanceAmount(0.0);
        }

        boolean doesAmountPaidExists = du.isThere(rs, "amount_paid");
        if (doesAmountPaidExists) {
            if (!rs.wasNull()) {
                data.setAmountPaid(rs.getDouble("amount_paid"));
            } else {
                data.setAmountPaid(0.0);
            }
        } else {
            data.setAmountPaid(0.0);
        }

        boolean doesCreatedByExists = du.isThere(rs, "created_by");
        if (doesCreatedByExists) {
            if (!rs.wasNull()) {
                data.setCreatedBy(ed.encrypt(String.valueOf(rs.getString("created_by"))));
            } else {
                data.setCreatedBy("");
            }
        } else {
            data.setCreatedBy("");
        }

        boolean doesUpdatedByExists = du.isThere(rs, "updated_by");
        if (doesUpdatedByExists) {
            if (!rs.wasNull()) {
                data.setUpdatedBy(ed.encrypt(String.valueOf(rs.getString("updated_by"))));
            } else {
                data.setUpdatedBy("");
            }
        } else {
            data.setUpdatedBy("");
        }
        data.setCreatedDate(rs.getTimestamp("created_date"));
//        boolean doesCreatedDateExists = du.isThere(rs, "created_date");
//        if (doesCreatedDateExists) {
//            if (!rs.wasNull()) {
//                data.setCreatedDate(rs.getTimestamp("created_date"));
//            } else {
//                data.setCreatedDate(null);
//            }
//        } else {
//            data.setCreatedDate(null);
//        }

        data.setUpdatedDate(rs.getTimestamp("updated_date"));
//        boolean doesUpdatedDateExists = du.isThere(rs, "updated_date");
//        if (doesUpdatedDateExists) {
//            if (!rs.wasNull()) {
//                data.setUpdatedDate(rs.getTimestamp("updated_date"));
//            } else {
//                data.setUpdatedDate(null);
//            }
//        } else {
//            data.setUpdatedDate(null);
//        }

        boolean doesPackageNameExists = du.isThere(rs, "package_name");
        if (doesPackageNameExists) {
            if (!rs.wasNull()) {
                data.setPackageName(rs.getString("package_name"));
            } else {
                data.setPackageName("");
            }
        } else {
            data.setPackageName("");
        }

        boolean doesColourAmountExists = du.isThere(rs, "colour_amount");
        if (doesColourAmountExists) {
            if (!rs.wasNull()) {
                data.setColourAmount(rs.getDouble("colour_amount"));
            } else {
                data.setColourAmount(0.0);
            }
        } else {
            data.setColourAmount(0.0);
        }

        boolean doesExShowroomAmountExists = du.isThere(rs, "ex_showroom");
        if (doesExShowroomAmountExists) {
            if (!rs.wasNull()) {
                data.setExShowroom(rs.getDouble("ex_showroom"));
            } else {
                data.setExShowroom(0.0);
            }
        } else {
            data.setExShowroom(0.0);
        }

        boolean doesPackageAmountExists = du.isThere(rs, "package_amt");
        if (doesPackageAmountExists) {
            if (!rs.wasNull()) {
                data.setPackageAmt(rs.getDouble("package_amt"));
            } else {
                data.setPackageAmt(0.0);
            }
        } else {
            data.setPackageAmt(0.0);
        }

        boolean doesSubTotalExists = du.isThere(rs, "sub_total");
        if (doesSubTotalExists) {
            if (!rs.wasNull()) {
                data.setSubTotal(rs.getDouble("sub_total"));
            } else {
                data.setSubTotal(0.0);
            }
        } else {
            data.setSubTotal(0.0);
        }

        boolean doesGstAmtExists = du.isThere(rs, "gst_amt");
        if (doesGstAmtExists) {
            if (!rs.wasNull()) {
                data.setGstAmt(rs.getDouble("gst_amt"));
            } else {
                data.setGstAmt(0.0);
            }
        } else {
            data.setGstAmt(0.0);
        }

        boolean doesGrossTotalExists = du.isThere(rs, "gross_total");
        if (doesGrossTotalExists) {
            if (!rs.wasNull()) {
                data.setGrossTotal(rs.getDouble("gross_total"));
            } else {
                data.setGrossTotal(0.0);
            }
        } else {
            data.setGrossTotal(0.0);
        }

        boolean doesSubsidyExists = du.isThere(rs, "subsidy_amt");
        if (doesSubsidyExists) {
            if (!rs.wasNull()) {
                data.setSubsidyAmt(rs.getDouble("subsidy_amt"));
            } else {
                data.setSubsidyAmt(0.0);
            }
        } else {
            data.setSubsidyAmt(0.0);
        }

        boolean doesTenureExists = du.isThere(rs, "tenure");
        if (doesTenureExists) {
            if (!rs.wasNull()) {
                data.setTenure(rs.getInt("tenure"));
            } else {
                data.setTenure(0);
            }
        } else {
            data.setTenure(0);
        }

        boolean doesExchangeExists = du.isThere(rs, "exchange");
        if (doesExchangeExists) {
            if (!rs.wasNull()) {
                data.setExchange(rs.getString("exchange"));
            } else {
                data.setExchange("No");
            }
        } else {
            data.setExchange("No");
        }

        boolean doesRefundStatusExists = du.isThere(rs, "refund_status");
        if (doesRefundStatusExists) {
            if (!rs.wasNull()) {
                data.setRefundStatus(rs.getString("refund_status"));
            } else {
                data.setRefundStatus("false");
            }
        } else {
            data.setRefundStatus("false");
        }

//         System.out.println("otp_verified " + rs.getBoolean("otp_verified"));
        data.setOtpVerified(rs.getBoolean("otp_verified"));

//        boolean doesOtpVerifiedExists = du.isThere(rs, "otp_verified");      
//        if (doesOtpVerifiedExists) {
//            if (!rs.wasNull()) {
//                data.setOtpVerified(rs.getBoolean("otp_verified"));
//            } else {
//                data.setOtpVerified(false);
//            }
//        } else {
//            data.setOtpVerified(false);
//        }
        boolean doesConsumerIdExists = du.isThere(rs, "consumer_vehicle_mapping_id");
//        System.out.println("rs " + rs.getLong("consumer_vehicle_mapping_id"));
        if (doesConsumerIdExists) {
            if (!rs.wasNull()) {
                data.setConsumerVehicleMappingId(ed.encrypt(String.valueOf(rs.getLong("consumer_vehicle_mapping_id"))));
            } else {
                data.setConsumerVehicleMappingId("false");

            }
        } else {
            data.setConsumerVehicleMappingId("false");
        }

//       System.out.println("rs " + rs.getBoolean("final_save"));
        data.setFinalSave(rs.getBoolean("final_save"));

//        boolean doesFinalSaveExists = du.isThere(rs, "final_save"); 
//        if (doesFinalSaveExists) {
//            if (!rs.wasNull()) {
//                data.setFinalSave(rs.getBoolean("final_save"));
//            } else {
//                data.setFinalSave(false);
//
//            }
//        } else {
//            data.setFinalSave(false);
//        }
        return data;
    }

}
