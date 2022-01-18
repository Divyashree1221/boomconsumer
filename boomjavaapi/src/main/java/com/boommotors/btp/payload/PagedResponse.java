/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.payload;

/**
 *
 * @author NandiniC
 */
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author rjanumpally
 */
@Getter
@Setter
@NoArgsConstructor
public class PagedResponse<T> {

    private List<T> content;
    private int page;
    private int size;
    private int totalElements;
    private int totalPages;
    private int next;


}
