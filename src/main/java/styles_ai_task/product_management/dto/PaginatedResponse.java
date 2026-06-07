package styles_ai_task.product_management.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedResponse<T> {

    private List<T> data;
    private long totalRecords;
    private int totalPages;
    private int pageNumber;
    private int pageSize;
}