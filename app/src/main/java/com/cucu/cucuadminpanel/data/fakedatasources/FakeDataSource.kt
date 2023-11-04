package com.cucu.cucuadminpanel.data.fakedatasources

import com.cucu.cucuadminpanel.data.models.items.ItemCategory
import javax.inject.Inject

class FakeDataSource @Inject constructor(){

    private val categoriesList = listOf(
    ItemCategory("Arte y artesanias"),
    ItemCategory("Articulos para viajes"),
    ItemCategory("Bebes"),
    ItemCategory("Belleza y accesorios"),
    ItemCategory("Cartucheras y carpetas"),
    ItemCategory("Cotillon"),
    ItemCategory("Electronica"),
    ItemCategory("Embalajes y descartables"),
    ItemCategory("Ferreteria"),
    ItemCategory("Fitness y tiempo libre"),
    ItemCategory("Higiene y limpieza"),
    ItemCategory("Hogar"),
    ItemCategory("Invierno"),
    ItemCategory("Jugueteria"),
    ItemCategory("Libreria"),
    ItemCategory("Libros"),
    ItemCategory("Marroquineria"),
    ItemCategory("Mascotas"),
    ItemCategory("Merceria"),
    ItemCategory("Mochilas"),
    ItemCategory("Navideño"),
    ItemCategory("Peluches"),
    ItemCategory("Simbolos patrios"),
    ItemCategory("Sin definir"),
    ItemCategory("Textil"),
    ItemCategory("Verano y agua")
)

    fun categoriesStringList(): List<String>{
        val list = mutableListOf<String>()
        categoriesList.forEach { list.add(it.category!!) }
        return list
    }

    /*val productsFakeList = mutableListOf(
        Product(
            id = "1", name = "Muñeca Barbie", newPrice = 600.0, oldPrice = 1000.0, stock = 8,
            img = "https://tiotomar.vtexassets.com/arquivos/ids/217822-800-800?v=638230576794670000&width=800&height=800&aspect=true",
            description = "Muñecas de colores para niñas, figuras de acción con juguetes clásicos, regalo para niñas,", code = 12345678910, category = ItemCategory("Arte y artesanias")
        ),
        Product(
            id = "2", name = "Triciclo", newPrice = 12500.0, oldPrice = 120.0, stock = 2,
            img = "https://dcdn.mitiendanube.com/stores/298/804/products/triciclo-turquesa111-9efb9d751b430d13bf16802919820422-640-0.jpg",
            description = "Producto prueba 2", code = 12345678910,  category = ItemCategory("Articulos para viajes")
        ),
        Product(
            id = "3", name = "Mega Garage", newPrice = 2200.0, oldPrice = 120.0, stock = 3,
            img = "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcRy9Hq7vF-jMbhN8QNZusoMHML38O2y4tWhUHf8KyFPCRLoOc36KBtMpNEJLa9JeNUSd1UGcadYlUaXvvkoSgEFCX9P7UQcLbVN6DZ7ibElQrq79QhBuDvg5Q&usqp=CAE",
            description = "Estación de Servicios Ideal Autos tamaño hotwhells Plástico resistente *4 puertas *", code = 12345678910,  category = ItemCategory("Belleza y accesorios")
        ),
        Product(
            id = "4", name = "Tren Vintage", newPrice = 2900.0, oldPrice = 3200.0, stock = 5,
            img = "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcT_0EkT53pjLHKDnV4ueAhX2pvofuW8R-PXC96Y5A7WNN5veN_ehtR-48vzM27Zi7l4mI7GQdWEcu7SFeDiUw_mgL55b1f-RYydOd7PTh_rE1-0N5uLwRzhrg&usqp=CAE",
            description = "Tren Grande Clásico Vintage Con Luz Y Sonido/Vías", code = 12345678910,  category = ItemCategory("Cartucheras y carpetas")
        ),
        Product(
            id = "5", name = "Avion a friccion", newPrice = 1800.0, oldPrice = 120.0, stock = 3,
            img = "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcSChuNYSIMi77EK31zuow8K2RcJ663EpZd91viRd28ejQTTOIpr_HmBiuGlsQ-sJ5WrySe7pU2mKcsd_PgLsCXGvH9AqmZ-sQ&usqp=CAY",
            description = "Increible Avion de juguete Grande a friccion. Cuenta con luz y sonido (funciona con 3 pilas LR44 (incluidas)).Ademas el avion se puede desarmar, para facil transporte", code = 12345678910,  category = ItemCategory("Cotillon")
        ),
        Product(
            id = "6", name = "Set completo soldaditos", newPrice = 1230.0, oldPrice = 120.0, stock = 6,
            img = "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcSYxenj8bJBGcgXoV_W_zaDG6caLaBCXw9fbcn-IDhEXM_i-m35HQQ8cGTdBhyeqkPQ6UUTV_P9hZCnqProPP46jns85P-w8_TGECxgvHYuDivNs6zvAa0xEg&usqp=CAE",
            description = "Special Forces Base Militar - Operación Rescate ¡Kit completo 12 accesorios! Juego Juguete de lujo para que los niños desarrollen la imaginación y creatividad sin limites.", code = 12345678910,  category = ItemCategory("Electronica")
        ),
)*/

    /*val purchasesFakeList = listOf(
    Purchase(
        id = "3",
        state = PurchaseState.PendingPayment().description,
        products = listOf(
            CartProduct(
                product = Product("1", "Barbie", 900.00, null, 4, "https://wwd.com/wp-content/uploads/2023/06/MEGA989664_002.jpg?w=1024", "Muñeca barbie edicion limitada con cartera re cheta", 123L),
                quantity = 2
            ),
            CartProduct(
                product = Product("2", "Ken", 660.00, null, 1, "https://acdn.mitiendanube.com/stores/991/977/products/diseno-sin-titulo-261-6a3bcf8103511d889816820229885354-640-0.png", "Muñeco gay", 123L),
                quantity = 1
            ),
            CartProduct(
                product = Product("3", "Batman", 2600.00, null, 2, "https://i.ebayimg.com/images/g/h0UAAOSwEAlk6ojR/s-l500.jpg", "Tiene la re plata", 123L),
                quantity = 1
            ),
            CartProduct(
                product = Product("4", "Oso teddy", 1900.00, null, 3, "https://media.traveler.es/photos/613767ec652b2e41f8dce491/1:1/w_896,h_896,c_limit/155694.jpg", "Re tierno", 123L),
                quantity = 3
            ),
            CartProduct(
                product = Product("5", "Spiderman", 2300.00, null, 1, "https://cdnlaol.laanonimaonline.com/web/images/productos/b/0000041000/41577.jpg", "El hombre que araña sapee", 123L),
                quantity = 1
            ),
            CartProduct(
                product = Product("6", "Messi", 199900.00, null, 1, "https://i.ebayimg.com/images/g/OZYAAOSwGnphsagG/s-l500.jpg", "El 1", 123L),
                quantity = 1
            )
        )
    ),
    Purchase(
        id = "2",
        state = PurchaseState.PreparingProducts().description,
        products = listOf(
            CartProduct(
                product = Product("4", "Oso teddy", 1900.00, null, 3, "https://media.traveler.es/photos/613767ec652b2e41f8dce491/1:1/w_896,h_896,c_limit/155694.jpg", "Re tierno", 123L),
                quantity = 1
            ),
            CartProduct(
                product = Product("5", "Spiderman", 2300.00, null, 1, "https://cdnlaol.laanonimaonline.com/web/images/productos/b/0000041000/41577.jpg", "El hombre que araña sapee", 123L),
                quantity = 3
            )
        )
    ),
    Purchase(
        id = "1",
        state = PurchaseState.Delivered().description,
        products = listOf(
            CartProduct(
                product = Product("1", "Hamaca", 80000.00, null, 1, "https://cdnlaol.laanonimaonline.com/web/images/productos/b/0000003000/3036.jpg", "Muñeca barbie edicion limitada con cartera re cheta", 123L),
                quantity = 4
            )
        )
    ),
)

    val fakePromoList = listOf(
        Promo(
            id = "1",
            name = "Combo escolar",
            description = "1 mochila + 1 carpeta + 1 cartuchera con lapicera lapiz y goma",
            stock = 8,
            price = 11000,
            products =
                listOf(
                    CartProduct(
                        product = Product("1", "Mochila", 900.00, null, 4, "https://wwd.com/wp-content/uploads/2023/06/MEGA989664_002.jpg?w=1024", "Muñeca barbie edicion limitada con cartera re cheta", 123L),
                        quantity = 1
                    ),
                    CartProduct(
                        product = Product("2", "Carpeta", 660.00, null, 1, "https://acdn.mitiendanube.com/stores/991/977/products/diseno-sin-titulo-261-6a3bcf8103511d889816820229885354-640-0.png", "Muñeco gay", 123L),
                        quantity = 1
                    ),
                    CartProduct(
                        product = Product("3", "Cartuchera", 2600.00, null, 2, "https://i.ebayimg.com/images/g/h0UAAOSwEAlk6ojR/s-l500.jpg", "Tiene la re plata", 123L),
                        quantity = 1
                    )
                )
        ),
        Promo(
            id = "2",
            name = "Combo escolar",
            description = "1 mochila + 1 carpeta + 1 cartuchera con lapicera lapiz y goma",
            stock = 5,
            price = 10000,
            products =
                listOf(
                    CartProduct(
                        product = Product("2", "Carpeta", 660.00, null, 1, "https://acdn.mitiendanube.com/stores/991/977/products/diseno-sin-titulo-261-6a3bcf8103511d889816820229885354-640-0.png", "Muñeco gay", 123L),
                        quantity = 1
                    ),
                    CartProduct(
                        product = Product("3", "Cartuchera", 2600.00, null, 2, "https://i.ebayimg.com/images/g/h0UAAOSwEAlk6ojR/s-l500.jpg", "Tiene la re plata", 123L),
                        quantity = 1
                    )
                )
        ),
        Promo(
            id = "3",
            name = "Combo escolar",
            description = "1 mochila + 1 carpeta + 1 cartuchera con lapicera lapiz y goma",
            stock = 2,
            price = 15000,
            products =
                listOf(
                    CartProduct(
                        product = Product("1", "Mochila", 900.00, null, 4, "https://wwd.com/wp-content/uploads/2023/06/MEGA989664_002.jpg?w=1024", "Muñeca barbie edicion limitada con cartera re cheta", 123L),
                        quantity = 1
                    ),
                    CartProduct(
                        product = Product("2", "Carpeta", 660.00, null, 1, "https://acdn.mitiendanube.com/stores/991/977/products/diseno-sin-titulo-261-6a3bcf8103511d889816820229885354-640-0.png", "Muñeco gay", 123L),
                        quantity = 1
                    ),
                    CartProduct(
                        product = Product("3", "Cartuchera", 2600.00, null, 2, "https://i.ebayimg.com/images/g/h0UAAOSwEAlk6ojR/s-l500.jpg", "Tiene la re plata", 123L),
                        quantity = 1
                    ),
                    CartProduct(
                        product = Product("4", "Cartuchera", 2600.00, null, 2, "https://i.ebayimg.com/images/g/h0UAAOSwEAlk6ojR/s-l500.jpg", "Tiene la re plata", 123L),
                        quantity = 1
                    )
                )
        ),
        Promo(
            id = "2",
            name = "Combo escolar",
            description = "1 mochila + 1 carpeta + 1 cartuchera con lapicera lapiz y goma",
            stock = 5,
            price = 10000,
            products =
            listOf(
                CartProduct(
                    product = Product("2", "Carpeta", 660.00, null, 1, "https://acdn.mitiendanube.com/stores/991/977/products/diseno-sin-titulo-261-6a3bcf8103511d889816820229885354-640-0.png", "Muñeco gay", 123L),
                    quantity = 1
                ),
                CartProduct(
                    product = Product("3", "Cartuchera", 2600.00, null, 2, "https://i.ebayimg.com/images/g/h0UAAOSwEAlk6ojR/s-l500.jpg", "Tiene la re plata", 123L),
                    quantity = 1
                )
            )
        ),
    )*/

    //fun productsByCategory(category:String) = productsFakeList.filter { it.category?.category == category }
}
